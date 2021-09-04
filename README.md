### FilmQueryProject
The program simulates a simplified version of the classic card game, Blackjack.

# Description
Once the game starts, it creates and shuffles a deck and deals out two cards to both player
and dealer. The player can then use their judgment to determine if they should hit or stay
while the dealer will play using house rules. Players can keep playing after every win or loss until 
they choose to exit the program.
# Technologies Used
-Enum
-ArrayLists
-Switch
-Scanner
-Inheritance
# How to Play
To play, once the cards have been dealt, players can either Hit (1) to get more cards and reach a value of 21
or they can opt to Stay (2) and not be dealt. If they Hit and their hand value is still less than 21, they
may choose Hit or Stay and if they are over 21, they bust.
# Problems and Solutions
The largest problem I encountered during this project was finding a way to properly 
simulate the rules of Blackjack. Building the logic around it required me to rework the structure
of my classes several times over and in the end deleting several features like a wallet to store a chip value.
# Biggest Takeaways
Don't get too attached to your code. I re-factored nearly every line I wrote at least once. 
And don't be too fixated on finding a specific way to make it work, if your program will run
without it then that's they way it will run. I was also so focused on the newer topics that older 
concepts like boolean values simply didn't come to mind at first and yet that ended up being central to my game logic.
