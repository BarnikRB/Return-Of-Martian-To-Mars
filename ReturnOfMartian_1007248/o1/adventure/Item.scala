package o1.adventure

/** The class `Item` represents items in a text adventure game. Each item has a name
  * and a  *  longer description. (In later versions of the adventure game, items may
  * have other features as well.)
  *
  * N.B. It is assumed, but not enforced by this class, that items have unique names.
  * That is, no two items in a game world have the same name.
  *
  * @param name         the item's name
  * @param description  the item's description */
abstract class Item(val name: String, val description: String,val effectOnHealth:Int) {

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  def typeOfItem: String


}
class Food(val foodName:String,val foodDescription:String, val foodEffectOnHealth:Int)extends Item (foodName,foodDescription,foodEffectOnHealth){
  def typeOfItem="Food"
}
class Clothing(val clotheName:String,val clotheDescription: String,val clotheEffectOnHealth:Int)extends Item (clotheName,clotheDescription,clotheEffectOnHealth){
  def typeOfItem="Clothing"
}
class Tool(val toolName:String,val toolDescription:String,val toolEffectOnHealth:Int)extends Item (toolName,toolDescription,toolEffectOnHealth){
  def typeOfItem="Tool"
}

