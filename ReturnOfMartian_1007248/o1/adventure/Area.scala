package o1.adventure

import scala.collection.mutable.Map
import scala.util.Random

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an "area" can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String) {
  private var isRiddleSolved= false
  private var hasRiddleBeenGenerated=false
  var riddleText=""
  var isRiddlePresent = true
  private var answer: Option[Int] = None
  private val neighbors = Map[String, Area]()
  private val itemsInArea= Map[String,Item]()
  private val mustUseItems= Map[String,Item]()

  def addItem(item: Item): Unit = {
    this.itemsInArea.put(item.name, item)
  }
  def addItems(itemList: Vector[Item]): Unit = {
    val a = itemList.map(item=> item.name->item)
    this.itemsInArea++= a
  }
  def contains(itemName: String): Boolean = {
    this.itemsInArea.contains(itemName)
  }
  def removeItem(itemName: String): Option[Item] = {

    this.itemsInArea.remove(itemName)

  }

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  def setMustUseItems(itemList: Vector[Item]): Unit ={
    val a = itemList.map(item=> item.name->item)
    mustUseItems++=a

  }
  def checkMustUseItems=this.mustUseItems
  def setInitialConditions()={
    hasRiddleBeenGenerated= false
    answer=None
    riddleText=""
    isRiddleSolved=false
  }
  def setRiddleSoled()={
    isRiddleSolved=true
  }


  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }


  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction--area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }


  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. The return
    * value has the form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES".
    * The directions are listed in an arbitrary order. */
  def fullDescription = {
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")

    if(itemsInArea.isEmpty){
      this.description + exitList+riddleTextInDescription()
    }
    else{
      val itemlist= "\nYou see here: "+this.itemsInArea.keys.mkString(" ")
      this.description+itemlist+exitList+riddleTextInDescription()
    }
  }
  private def riddleTextInDescription():String={
    if(isRiddlePresent&&(!hasRiddleBeenGenerated)){
      "\n\nYou need to generate a riddle and solve it before you can proceed to neighbouring areas."
    }else if(isRiddlePresent&&hasRiddleBeenGenerated&& !isRiddleSolved){
      riddleText
    }else if(isRiddleSolved){
      "\n\nYou have already solved the riddle. You can now proceed."
    }
    else{
      "You don't need to generate any riddle here."
    }

  }
  def riddleStatus=hasRiddleBeenGenerated
  def checkRiddlePresentString(): String={
     if(!isRiddlePresent){
      ("There is no riddle to solve.")

    }else if(isRiddlePresent && answer.isEmpty){
       ("Please generate the riddle before trying to solve it")

     }else{
       ""
     }
  }
  def checkRiddlePresent: Boolean={
     if(!isRiddlePresent){
      false

    }else if(isRiddlePresent && answer.isEmpty){
       false

     }else{
       true
     }
  }
  def tryRiddle(input: String):Boolean ={

    if(input.contains(".")){

      false
    }else{
      val a = input.toIntOption
      a match {
        case Some(x)=> {x==answer.get }
        case None => false
      }
    }
  }

  def generateRiddle()={
    if(isRiddlePresent&&(!hasRiddleBeenGenerated)){
      val firstNumber= Random.nextInt(10000)
      val secondNumber= Random.nextInt(10000)
      val listOfOperators=Vector('+','-','*')
      val randomOperator= listOfOperators.lift(Random.nextInt(3))
      var result = 0
      randomOperator match {
        case Some('+')=> result= firstNumber+secondNumber
        case Some('-')=> result= firstNumber-secondNumber
        case Some('*')=> result= firstNumber*secondNumber
        case None =>
      }
      answer= Some(result)
      val questionText = s"${firstNumber} ${randomOperator.get} ${secondNumber}"
      riddleText=("\n\nPlease solve the follwing problem\n"+questionText)
      hasRiddleBeenGenerated=true

    }

  }


  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)



}
