package o1.adventure


import scala.collection.mutable.Map
import scala.math._

/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area, val maxHealthPoints: Int) {
  val negativePointsForMissingItems= maxHealthPoints/10
  private var restLimit = 2
  private var foodConsumed=Vector[Item]()
  private val itemsInUse = Map[String,Item]()
  private var currentHealthPoints = maxHealthPoints
  private var negativePoints=maxHealthPoints/100
  private var canCrossArea = false
  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val itemInPossession= Map[String,Item]()
  def get(itemName: String): String ={
    if(this.currentLocation.contains(itemName)){
      var a = currentLocation.removeItem(itemName)
      this.itemInPossession+= itemName->a.get
      s"You pick up the ${itemName}."

    }else{
      s"There is no ${itemName} here to pick up."
    }
  }
  def has(itemName: String): Boolean={
    itemInPossession.contains(itemName)

  }
  def show()={
    if(itemsInUse.isEmpty){
      "You are not using any item right now."
    }else{
      "The items you are using are listed below:\n"+itemsInUse.keys.mkString("\n")
    }
  }
  def canCross=canCrossArea
  def usingItem(itemname:String)={
    itemsInUse.contains(itemname)
  }
  def health()={
    s"Your health points is ${currentHealthPoints}."
  }
  def healtInt=currentHealthPoints
  def inventory: String={
    if(itemInPossession.isEmpty){
      "You are empty-handed."
    }else{
      "You are carrying:\n"+itemInPossession.keys.mkString("\n")
    }
  }
  private def foodInInventory={
    itemInPossession.toVector.map(_._2).filter(_.typeOfItem=="Food")
  }
  def food:String={
    if(foodInInventory.nonEmpty){
      "You are carrying the following food items:\n"+foodInInventory.map(_.name).mkString("\n")
    }else{
      "You don't have any food item."
    }
  }
  private def clotheInInventory={
    itemInPossession.toVector.map(_._2).filter(_.typeOfItem=="Clothing")

  }
  def clothings:String={
    if(clotheInInventory.nonEmpty){
      "You are carrying the following clothing items:\n"+clotheInInventory.map(_.name).mkString("\n")
    }else{
      "You don't have any clothing item."
    }
  }
  private def toolsInInventory={
    itemInPossession.toVector.map(_._2).filter(_.typeOfItem=="Tool")

  }
  def tools:String={
    if(toolsInInventory.nonEmpty){
      "You are carrying the following tools:\n"+toolsInInventory.map(_.name).mkString("\n")
    }else{
      "You don't have any tools."
    }
  }
  def use(itemName: String)={
    if(!has(itemName)&&(!itemsInUse.contains(itemName))){
      s"You don't have ${itemName} in your inventory.\n You need to get it in your inventory before you can use it."
    }else if(foodInInventory.map(_.name).contains(itemName)){
      s"You can't use ${itemName} as it is a food. You need to eat it"
    }else if((itemsInUse.contains(itemName))){
      s"${itemName} is already in use."
    }else{
      if(itemsInUse.size<3){
        var a = itemInPossession.remove(itemName)
        itemsInUse += a.get.name.trim->a.get
        s"You are now using ${itemName}."
      }else{
        "You can't use more than two items at a time. Try removing one item."
      }

    }
  }
  def removeItemFromUse(itemName:String)={
    if(itemsInUse.contains(itemName)){
      var a = itemsInUse.remove(itemName)
      itemInPossession += a.get.name->a.get
      s"You stopped using ${itemName} and stored it in your inventory."
    }else{
      "You are not using the item."
    }
  }
  def eat(itemName: String)={
    if(!has(itemName)){
      s"You don't have ${itemName} in your inventory.\nYou need to get it in your inventory before you can eat it."
    }else if(foodInInventory.contains(itemInPossession(itemName))){
      val a = itemInPossession.remove(itemName)
      val b = foodEffectOnHealth(a.get)

      foodConsumed = a.get +: foodConsumed

      s"You ate ${itemName} as it is a food."+b

    }else if(foodConsumed.map(_.name).contains(itemName)){
      "You have already consumed " + itemName+". So, you can't consume it again."
    }
    else{
      s"You can't eat ${itemName}."
    }
  }
  def foodEffectOnHealth(itemName:Item):String={
    val startingPoint = currentHealthPoints
    currentHealthPoints= alterHealthValue(itemName.effectOnHealth)
    if(itemName.effectOnHealth>0){
      if(startingPoint==currentHealthPoints){
        "You have max number of health points. It can't increase anymore"
      }else{
        s"\nThe consumption of ${itemName.name} has increase your health by ${currentHealthPoints-startingPoint}.\nYour current health points is ${currentHealthPoints}."

      }

    }else if (itemName.effectOnHealth<0){
      s"You were intoxicated by the consumption of ${itemName.name}.\nYour health points got reduced by ${startingPoint-currentHealthPoints}.\n Your current health points is ${currentHealthPoints}."
    }else{
      s"The consumption of ${itemName.name} had no effect on your health.\nYour current health points is ${currentHealthPoints} "
    }

  }
  def alterHealthValue(number: Int):Int={
    var points = currentHealthPoints
    points+=(number)
    val b = max(0,points)
    points=min(b,maxHealthPoints)
    points
  }
  def checkPreseceOfMustHaveItems(location: Area):Boolean={
    if(location.checkMustUseItems.isEmpty){
      true
    }else{
      val vectorOfBooleans =itemsInUse.toVector.map(_._1).map(location.checkMustUseItems.contains(_))

      vectorOfBooleans.count(_==true)==location.checkMustUseItems.size
    }

  }
  def checkMissingMustHaveItems(location: Area): Int={
    if(location.checkMustUseItems.isEmpty){
      (0)
    }else{
      val vectorOfBooleans=itemsInUse.toVector.map(_._1).map(location.checkMustUseItems.contains(_))
      val missingRecommendedItems=location.checkMustUseItems.size-vectorOfBooleans.count(_==true)
      missingRecommendedItems
    }

  }

  def examine(itemName: String): String={
    if(itemInPossession.contains(itemName)){
      s"You look closely at the ${itemName}.\n"+itemInPossession(itemName).description
    }else{
      "If you want to examine something, you need to pick it up first."
    }
  }
  def drop(itemName: String): String={
    if(itemInPossession.contains(itemName)){
      var a = itemInPossession.remove(itemName)
      currentLocation.addItem(a.get)
      s"You drop the ${itemName}."
    }else{
      "You don't have that!"
    }
  }
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String):String = {
    if(canCrossArea ){
      val destination = this.location.neighbor(direction)
      if(destination.isDefined && checkPreseceOfMustHaveItems(destination.get)){
        val beginnigLocation = this.currentLocation
        this.currentLocation = destination.getOrElse(this.currentLocation)
        canCrossArea= false
        beginnigLocation.setInitialConditions()
        val minusPoints=((itemInPossession.size)*negativePoints)
        currentHealthPoints=alterHealthValue(-minusPoints)
        if(minusPoints==0){
          s"You go ${direction}"
        }else{
          s"You go ${direction}.\nDue to physical exersion your health points were reduced by ${minusPoints} points."

        }
      }else if(destination.isDefined && !checkPreseceOfMustHaveItems(destination.get)){
        s"It is mandatory to use all of the items listed below if you want to go ${direction}\n"+destination.get.checkMustUseItems.toVector.map(_._1).mkString("\n")+s"\nYou aren't using ${checkMissingMustHaveItems(destination.get)} of the items listed above."+"\nYou can use the \"advance DIRECTION\" command if you want to travel whatsoever."
      }else{
        s"You can't go ${direction}."
      }

    }else if (location.isRiddlePresent && !canCrossArea){
      ("You need to deal with the riddle before you can cross "+location.name+".")

    }else{
      ""
    }


  }
   def advance(direction: String)={
     val destination = this.location.neighbor(direction)
     if(canCrossArea&&destination.isDefined && !checkPreseceOfMustHaveItems(destination.get)){
       val beginnigLocation = this.currentLocation
       this.currentLocation = destination.getOrElse(this.currentLocation)
       val startingPoints = currentHealthPoints
       canCrossArea= false
       beginnigLocation.setInitialConditions()
       currentHealthPoints=alterHealthValue(-this.checkMissingMustHaveItems(destination.get)*negativePointsForMissingItems)
       s"You advance towards the ${direction} direction. But you lost ${startingPoints-currentHealthPoints} for not having ${checkMissingMustHaveItems(destination.get)} mandatory-to-use items."

     }else{
       s"You can't advance towards ${direction} direction."

     }

   }
  def generate(input: String)={
    if(input.toLowerCase == "riddle"&&(!location.riddleStatus)){
      location.generateRiddle()
      "You generated a  riddle."
    }else if(input.toLowerCase == "riddle"&&(location.riddleStatus)){
      "You have already generated the riddle. You can't generate it again."
    }else{
      s"You can't generate a ${input}."
    }
  }


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    if(restLimit>0){
      if(currentHealthPoints==maxHealthPoints){
        "You have max health. You don't need to rest."
      }else{

        val a = currentHealthPoints
        currentHealthPoints=alterHealthValue((4*maxHealthPoints)/100)
        restLimit-=1
        s"You rest for a while. Your health points increase by ${a-currentHealthPoints}.\nYou can only rest ${restLimit} more times."

      }
         }else{
      "You can't rest anymore."

    }

  }


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }
  def solveRiddle(modifier: String):String={

    val a = this.location.checkRiddlePresent
    if(a&&(!canCrossArea)){
      val b = currentLocation.tryRiddle(modifier)
      if(b){
        location.setRiddleSoled()
        canCrossArea= true
        currentHealthPoints=alterHealthValue(negativePoints)
        "Correct answer! You can now cross the area."
      }else{
        canCrossArea= false
        currentHealthPoints=alterHealthValue(-negativePoints)
        s"Incorrect answer!\nYour health points will be penalised by ${negativePoints} points.\nPlease insert your answer in integer form."

      }
    }else if(a&&(canCrossArea)){
      "You have already given the correct answer."
    }else{
      this.location.checkRiddlePresentString()
    }

  }
  def help={
    "You, the Martian will have thousand health points at the beginning.\nYou will be using a Martian-GPS to navigate.  Without using the GPS, you can't travel.\nHowever, when you enter a new area the GPS gets locked.\nIn order to unlock the GPS, a mathematical riddle has to be generated and solved.\nIn order to generate the riddle type \"generate riddle\".\nThe riddle will then appear on screen and to submit its answer, type \"try ANSWER\",\nwhere ANSWER is the solution to answer in integer form.\nCorrect answer will be rewarded by 10 health point while incorrect answers will be penalised by same amount.\nFood, Clothing and Tool are three types of items.Food items can be eaten if they are in inventory. \nClothing and Tool items can be used if present in inventory and removed back to inventory.\nTo eat a food item, type \"eat ITEM\" where ITEM is name of food. To use and then remove item, type \"use ITEM\" and \n\"remove ITEM\" where ITEM is name of clothing or tool.\nTo get a list of foods,clothings,tools, and items in use type \"food\",\"clothings\" ,\"tools\" and \"show\" respectively.\nBefore going to an area, you might have keep some items in use.\nIf you want to go inspite of not using those items type \"advance DIRECTION\" where direction is your desired exit \nbut you will be penalised for not using those items. You can also use \"rest\" to increase your health points."
  }


  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name


}


