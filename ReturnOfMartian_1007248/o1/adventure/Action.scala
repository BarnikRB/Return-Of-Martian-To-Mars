package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim
  private val verb      = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match {
    case "go"    => Some(actor.go(this.modifiers)+"\n"+actor.health())
    case "rest"  => Some(actor.rest()+"\n"+actor.health())
    case "quit"  => Some(actor.quit()+"\n"+actor.health())
    case "inventory"=>Some(actor.inventory+"\n"+actor.health())
    case "get"=>Some(actor.get(this.modifiers)+"\n"+actor.health())
    case "examine"=> Some(actor.examine(this.modifiers)+"\n"+actor.health())
    case "drop"=> Some(actor.drop(this.modifiers)+"\n"+actor.health())
    case "generate"=> Some(actor.generate(this.modifiers)+"\n"+actor.health())
    case "try"=> Some(actor.solveRiddle(this.modifiers)+"\n"+actor.health())
    case "eat"=> Some(actor.eat(this.modifiers)+"\n"+actor.health())
    case "use"=> Some(actor.use(this.modifiers)+"\n"+actor.health())
    case "remove"=> Some(actor.removeItemFromUse(this.modifiers)+"\n"+actor.health())
    case "health"=> Some(actor.health())
    case "advance"=> Some(actor.advance(this.modifiers)+"\n"+actor.health())
    case "food"=> Some(actor.food+"\n"+actor.health())
    case "clothings"=> Some(actor.clothings+"\n"+actor.health())
    case "tools"=>Some(actor.tools+"\n"+actor.health())
    case "show"=> Some(actor.show()+"\n"+actor.health())
    case "help"=> Some(actor.help)
    case other   => None
  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

