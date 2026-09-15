## NEW TEST LIST
-- Game setup and heroes
[ DONE ] FINDUS plays first
[ DONE ] PEDDERSEN is after FINDUS
[ DONE ] FINDUS is after PEDDERSEN (redundant maybe?)
[ DONE ] Turn number starts at 0 and increments by 1 each endTurn()
[ DONE ] Both heroes start with 21 health (HERO_MAX_HEALTH)
[ DONE ] Hero type is "Baby" (BABY_HERO_TYPE) with description "Just Cure"
[ DONE ] Hero power costs 2 mana (HERO_POWER_COST) and does nothing in AlphaStone
[ DONE ] Hero non-active but no check for power only once per turn (said it in the book)
[ DONE ] Hero mana resets to 3 at the start of their turn
[ DONE ] getHand() should return the respective player's hand
[ DONE ] getField() should return the respective player's field

-- Decks, hands and drawing
[ DONE ] Both players have a 7-card deck (attributes added already)
[ DONE ] Both players start with 3-card hand: Uno, Dos, Tres
[ DONE ?] Cards have no effects or spells in AlphaStone
[ DONE ] At the start of a turn, the active player draws 1 card from their deck
[ DONE ] Drawn cards enter the hand at index 0, pushing existing cards in +1 index

-- Playing cards/minions
[ DONE ] Playing a card costs mana equal to the card's mana cost
[ DONE ] Playing a card moves it from the player's hand to the field at 'atIndex'
[ DONE ] Minions enter the field inactive
[ DONE ] Fielded minions become active at the start of their owner's turn
[ DONE ? ] Cards in hand are always inactive

-- Combat and attacks
[ DONE] Inactive minions cannot attack
[ DONE ] A minion can attack only once per turn, becoming inactive after attacking
[ DONE ] Minion cs minion: both deal simultaneous damage equal to their attack to each other's health
[ DONE ] Defeated minions (health <= 0) are removed from the field immediately
[ DONE ] Minion vs hero: deals damage equal to minion's attack on opponent's hero
[ DONE ? ] Heroes do not counter-attack

-- Win condition
[ DONE ] Findus wins at the start of turn 8 (after 4 completed rounds)
[ DONE ] getWinner() returns null while progressing, or the winning player

-- Return statuses
done for playCard() and attackCard()
[ DONE ] Return Status.OK on successful mutator execution
[ DONE ] Return Status.NOT_PLAYER_IN_TURN if action is attempted out of turn
[ DONE ] Return Status.NOT_OWNER if interacting with an opponent's card or hero
[ DONE ] Return Status.NOT_ENOUGH_MANA if hero lacks required mana.
[ DONE ] Return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE-MINION if minion is inactive
[ DONE ] Return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION if targeting a friendly minion
[ DONE ] Return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND if hero power is reused in one turn

-- GammaStone
[ ] 2 hero types
[ ] Findus ThaiChef, Peddersen DanishChef
[ ] ThaiChef hero power decrease op hero health by 2
[ ] DanishChef hero power summon Sovs card (1, 1)
[ ] Winner when op field is empty after round 3
[ ]  if both players' fields are empty on round 3, findus wins
