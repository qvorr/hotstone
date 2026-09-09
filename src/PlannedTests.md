### ITERATION 1 

[x] Given an initialized game, Then Findus is player in turn
[x] When Findus ends its turn, Then it is Peddersen in turn
[x] When Peddersen ends its turn, Then it is Findus in turn
[x] Given Card Dos Then it has attributes (2,2,2)
[x] Findus turn number should be 1 and the Peddersen 2, then Findus 3
[X] When Findus plays a card, Then Peddersen still has 3 cards in his hand.
[X] Findus has 3 cards, and card with index 0 is Tres
[X] Findus has 3 cards, and card with index 1 is Dos
[X] Findus has 3 cards, and card with index 2 is UNO

### ITERATION 2
[x] getOwner method in StdCard is working properly
[x] setName method in StdCard
[ ] play card and end turn and check mana
[ ] playing card mana depletion
[ ] findus wins after 4 turns
[ ] if findus playe Tres and peddersen played Dos, dos is removed from the game



## NEW TEST LIST
-- Game setup and heroes
[ DONE ] FINDUS plays first
[ DONE ] PEDDERSEN is after FINDUS
[ DONE ] FINDUS is after PEDDERSEN (redundant maybe?)
[ DONE ] Turn number starts at 0 and increments by 1 each endTurn()
[ DONE ] Both heroes start with 21 health (HERO_MAX_HEALTH)
[ ] A hero dies and loses when health reaches 0 or below
[ DONE ] Hero type is "Baby" (BABY_HERO_TYPE) with description "Just Cure"
[ ] Hero power costs 2 mana (HERO_POWER_COST) and does nothing in AlphaStone
[ ] Hero non-active but no check for power only once per turn (said it in the book)
[ DONE ] Hero mana resets to 3 at the start of their turn

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
[ ] A minion can attack only once per turn, becoming inactive after attacking
[ DONE ] Minion cs minion: both deal simultaneous damage equal to their attack to each other's health
[ DONE ] Defeated minions (health <= 0) are removed from the field immediately
[ ] Minion vs hero: deals damage equal to minion's attack opponent's hero
[ ] Heroes do not counter-attack

-- Win condition
[ ] Findus wins at the start of turn 8 (after 4 completed rounds)
[ ] getWinner() returns null while progressing, or the winning player

-- Return statuses
done for playCard() and attackCard()
[ ] Return Status.OK on successful mutator execution
[ ] Return Status.NOT_PLAYER_IN_TURN if action is attempted out of turn
[ ] Return Status.NOT_OWNER if interacting with an opponent's card or hero
[ ] Return Status.NOT_ENOUGH_MANA if hero lacks required mana.
[ ] Return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE-MINION if minion is inactive
[ ] Return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION if targeting a friendly minion
[ ] Return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND if hero power is reused in one turn