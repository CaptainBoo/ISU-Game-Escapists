Readme.txt – Eddie Liu and Estar Guan - Recreating The Escapists 2

Responsibilities:
Creating the map - Estar
Inventory system- Estar
Guards and heat system- Eddie
Items - Eddie
Finishing the escape (plot) - Estar
Drawers system - Estar
crafting system - Estar
Making the menus for the game- Eddie
Adding sounds to the game- Eddie
Animations - Estar
NPC - Eddie
Path Finding - Eddie
Contraband/Contraband Detector - Estar
Collisions - Estar

Hints:
- We don't have any hints, our game can be completed really quickly
- There's no reason to go to the left side of the map since there are a lot of drawers on the right

Missing functions:
- Interaction with other prisoners
	- No shop system, no opinion
- Player attributes like strength, agility, and intelligence
	- We would have added these features to the prisoner class but 
- Routines and rollcall

Additional functions:
- Creating movement animations for prisoners and character
- we never detailed how we would do the collision
	- All walls are added properly
	- A lot of hard coding was involved using arrays
- Contraband detectors are on the map. If you walk through them with contraband items, then you'll eventually be chased by a guard
- We have complex pathfinding for the NPCs
	- They find the shortest path using BFS
- We manipulated the JFrame coordinates to keep the player in the middle and not change the position of other objects like NPCs and the map

Known bugs:
- Some of the collisions with the walls are off by a few pixels since the entire map doesn't lie on solid integer values (the size of each tile is a decimal value).
- The pathfinding of the NPCs is off because of the aforementioned bug where the map doesn't align with solid integer values grid
- This can be easily fixed but unfortunately it's too time consuming to make another map
-While already moving, pressing another movement key sometimes makes our character frames go crazy for a few seconds and then returns to normal. I had the same bug last year and still don't know how to fix it to this day.


Bonus Notes:
- From Estar: I know the exact reason why the map is off by a few pixels. I built the map image with a combination of different screenshots to ensure quality and lots of pixels. However lining up the screenshots on google drawing is difficult so the left Side and right Side follow different scales.

-Due to the map's weird scaling and everything being a few pixels off. Some of the drawers especially top side ones you need to press the top left corner to open them. It's a really easy fix all I need to do is change 3 other tiles in the map to be drawers but that's like another 3x16 lines of not very useful code.
