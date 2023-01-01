package o1.adventure
import scala.util.Random

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure {

  /** The title of the adventure game. */
  val title = "Martian's return to mars."
  private val bottomMountain = new Area("Bottom Of Mountain", "You are facing a cold icy mountain.It is extremely cold here.\nThere is nothing around. It seems like land begins from the cliff of the mountain")
  private val cliffMountain = new Area("Cliff Of Mountain","You are on the cliff of mountain.\nYou can see walkable land in front of you.")
  private val middleDesert = new Area("Desert","You are somewhere in the middle of desert.\nThe scorching heat from the sun is burning your skin.")
  private val northDesert = new Area("Desert","You are somewhere in the northern part of the desert.\nThe scorching heat from the sun is burning your skin.")
  private val southDesert = new Area("Desert","You are somewhere in the southern part of the desert.\nThe scorching heat from the sun is burning your skin.")
  private val easternForest = new Area("Forest of Doom","You are amidst a dense forest’s eastern section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 3 steps away from the space station.")
  private val northEasternForest = new Area("Forest of Doom","You are amidst a dense forest's north-eastern section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 4 steps away from the space station.")
  private val southEasternForest = new Area("Forest of Doom","You are amidst a dense forest's south-eastern section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 4 steps away from the space station.")
  private val centralForest = new Area("Forest of Doom","You are amidst a dense forest's central section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 2 steps away from the space station.")
  private val northernForest = new Area("Forest of Doom","You are amidst a dense forest’s northern section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 3 steps away from the space station.")
  private val southernForest = new Area("Forest of Doom","You are amidst a dense forest’s southern section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 3 steps away from the space station.")
  private val westernForest = new Area("Forest of Doom","You are amidst a dense forest’s western section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 1 step away from the space station.")
  private val northWesternForest = new Area("Forest of Doom","You are amidst a dense forest's north-western section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 2 steps away from the space station.")
  private val southWesternForest = new Area("Forest of Doom","You are amidst a dense forest.'s south-western section. You are on swampy ground surrounded by vines and trees. Beware of picking and eating any food.\nHalf of the food are poisonous and half of the food are edible. You are 3 steps away from the space station.")
  private val spaceStation=new Area("Space Station","You are in the space station. This is the only area from which you can get to your home.\nYou must deal with the riddle, use your Golden Key and have over 750 health points in order to use the rocket.")
  private val destination = spaceStation
 val forestList=Vector(easternForest,northEasternForest,southEasternForest,centralForest,northernForest,southernForest,westernForest,northWesternForest,southWesternForest)
  private val rope = new Tool("Rope","You use it for going upwards or downwards.",0)
  private val jacket = new Clothing("Jacket","You use it to fight with the cold.",0)
  private val hat = new Clothing("Hat","You use it to protect yourself from sun.",0)
  private val sunscream=new Tool("Sunscream","You use it to prevent getting sun burns.",0)
  private val goldenKey= new Tool("Golden Key","I am your way out.",0)
  private val machette = new Tool("Machette","You use it to cut through the vines in forest.",0)
  private val wellingtonBoots= new Tool("Wellington Boots","You use it to pass thrrough swampy grounds.",0)
  private val apple = new Food("Apple","An apple a day keeps the doctor away.",75)
  private val orange = new Food("Orange","It is a nutrious citrousy fruit.",75)
  private val cashewFruits= new Food("Cashew Fruits","This is the fruit that produces creamy cashew nuts but for some obvious reasons humans avoid consuming it.",-1000)
  private val grapes = new Food("Grapes","It is a cluster of sweet and sour green berries.",75)
  private val mango = new Food("Mango","It is a very sweet fruit with yellow flesh and a large seed.",75)
  private val peach = new Food("Peach", "It is a delecious and healthy fruit.",75)
  private val kiwi= new Food("Kiwi","It has a brown outerskin, green insides and is expensive to purchase.",75)
  private val papaya= new Food("Papaya", "It is a nutritious fruit rich in vitamin-A.",75)
  private val carrot=new Food ("Carrot","It is a crunchy yet nutritious vegetable",75)
  private val bakedPotatoes= new Food("Baked Potatoes","Baked vegetables filled with carbohydrates.",75)
  private val goldenApple= new Food("Golden Apple","Enhanced apple that provides four times more energy.",300)
  private val appleSeeds= new Food("Apple Seeds","Research has found that seed of apples contain cyanide.",-1000)
  private val rhubarbLeaves= new Food("Rhubarb Leaves","Its stems are quite popular yet no one consumes the leaves.",-1000)
  private val rottenTomatoes=new Food("Rotten Tomatoes","Decaying tomatoes ridden with toxic bacteria and fungi.",-1000)
  private val wierdMushrooms= new Food("Weird Mushroom","There are many mushrooms that are edible and many that are lethal.\nIt is better to stay safe than sound.",-1000)
  private val poisonousBerries=new Food("Poisonous Berries","The name spills the beans. Decide wisely.",-1000)
  private val bitterAlmonds = new Food("Bitter Almonds","Contains high levels of precursors to cyanide.",-1000)
  private val rawKidneyBeans= new Food("Raw Kidney Beans","They contains high levels of lectins taht can cause food poisoning",-1000)
  private val cherryPits=new Food("Cherry Pits","They are toxic.",-1000)
  bottomMountain.setNeighbors(Vector("upwards"->cliffMountain))
  cliffMountain.setNeighbors(Vector("downwards"->bottomMountain,"west"->middleDesert))
  middleDesert.setNeighbors(Vector("east"->cliffMountain,"north"->northDesert,"south"->southDesert,"west"->easternForest))
  northDesert.setNeighbors(Vector("south"->middleDesert,"west"->northEasternForest))
  southDesert.setNeighbors(Vector("north"->middleDesert,"west"->southEasternForest))
  easternForest.setNeighbors(Vector("east"->middleDesert,"west"->centralForest,"north"->northEasternForest,"south"->southEasternForest))
  northEasternForest.setNeighbors(Vector("south"->centralForest,"west"->northernForest,"east"->northDesert))
  southEasternForest.setNeighbors(Vector("north"->centralForest,"west"->southernForest,"east"->southDesert))
  centralForest.setNeighbors(Vector("north"->northernForest,"south"->southernForest,"east"->easternForest,"west"->westernForest))
  northernForest.setNeighbors(Vector("south"->centralForest,"east"->northEasternForest,"west"->northWesternForest))
  southernForest.setNeighbors(Vector("north"->centralForest,"east"->southEasternForest,"west"->southWesternForest))
  westernForest.setNeighbors(Vector("north"->northWesternForest,"south"->southWesternForest,"east"->centralForest,"west"->spaceStation))
  northWesternForest.setNeighbors(Vector("south"->westernForest,"east"->northernForest))
  southWesternForest.setNeighbors(Vector("north"->westernForest,"east"->southernForest))
  spaceStation.setNeighbors(Vector("east"->westernForest))

  cliffMountain.setMustUseItems(Vector(rope,jacket))
  bottomMountain.setMustUseItems(Vector(rope))
  middleDesert.setMustUseItems(Vector(sunscream,hat))
  northDesert.setMustUseItems(Vector(sunscream,hat))
  southDesert.setMustUseItems(Vector(sunscream,hat))
  easternForest.setMustUseItems(Vector(machette,wellingtonBoots))
  northEasternForest.setMustUseItems(Vector(machette,wellingtonBoots))
  southEasternForest.setMustUseItems(Vector(machette,wellingtonBoots))
  centralForest.setMustUseItems(Vector(machette,wellingtonBoots))
  northernForest.setMustUseItems(Vector(machette,wellingtonBoots))
  southernForest.setMustUseItems(Vector(machette,wellingtonBoots))
  westernForest.setMustUseItems(Vector(machette,wellingtonBoots))
  northWesternForest.setMustUseItems(Vector(machette,wellingtonBoots))
  southWesternForest.setMustUseItems(Vector(machette,wellingtonBoots))
  spaceStation.setMustUseItems(Vector(machette,wellingtonBoots))

  bottomMountain.addItems(Vector(rope,jacket))
  cliffMountain.addItems(Vector(hat))
  middleDesert.addItems(Vector(sunscream))
  northDesert.addItems(Vector(machette))
  southDesert.addItem(wellingtonBoots)
  easternForest.addItems(Vector(kiwi,wierdMushrooms))
  northEasternForest.addItems(Vector(bakedPotatoes,appleSeeds))
  southEasternForest.addItems(Vector(carrot,bitterAlmonds))
  centralForest.addItems(Vector(rottenTomatoes,papaya))
  northernForest.addItems(Vector(grapes,cherryPits))
  southernForest.addItems(Vector(apple,rhubarbLeaves))
  westernForest.addItems(Vector(orange,rawKidneyBeans))
  northWesternForest.addItems(Vector(mango,cashewFruits))
  southWesternForest.addItems(Vector(peach,poisonousBerries))
  val randomIndex= Random.nextInt(9)
  forestList.lift(randomIndex).get.addItems(Vector(goldenApple,goldenKey))

  /** The character that the player controls in the game. */
  val player = new Player(bottomMountain,1000)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */



  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = (this.player.location == this.destination)&&this.player.canCross&&(this.player.healtInt>=((75*player.maxHealthPoints)/100))&&player.usingItem("Golden Key")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.player.healtInt==0

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "\n\nYou are a Martian trapped in a earth.\nYour only way back to home is the rocket in space station, where you must reach to win the game.\nHowever,to use the rocket you would need a Golden Key, which can be found somewhere in Forest Of Doom.\nTo know more about how to play the game, type \"help\"."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      "The rocket is taking off. Mother Mars, here I come."
    else if (this.player.healtInt==0)
      "Oh no! Your health point has reduced to zero for whichyou passed away.\nGame Over!"

    else  // game over due to player quitting
      "Quitter!"
  }


  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}

