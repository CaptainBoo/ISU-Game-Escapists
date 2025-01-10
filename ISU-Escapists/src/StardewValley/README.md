– If worked with a partner, list the responsibilities of each person
No partner

– Hints on how to play (skip levels?)
No hints in particular. Just know that watering the plants reduces its plant time by 5 seconds (300 frames)
Also if you spend all your money at the start to buy seeds you can get money a lil faster.

- Any functionalities missing from your original plan for the game
Due to the fact the collision system took SO SO SO LONG to code and had SO MANY BUGS I had to scrath the mob
fighting idea and make the farming idea.
 
The reason I chose the farming idea even though it would probably have been harder to write is because I thought it represented Stardew Valley more

I also failed to implement the health bar but that is because there was no point in doing so because there are no mobs. In fact you can see I was going to add a health bar if you scroll down to the very last line in my code

- Any additional functionalities added from your original plan
There a lot of minor details that I added to my game. For example I added the little popups when you pick stuff up. The water filling feature. The watering plants. The 4 different log shapes sizes and types and thats about it.

- Known bugs / errors in your game
When you press and hold the d/right key and then tap the s/down key the character does a 360 spin

If you go up to a border boundary and then run directly either down or up add it and then start to go diagonally, you will go slightly more into the object pass the boundary and get stuck there. The only way to get unstuck is to go the opposite direction. If you were going down left then going up is how you get out and vice versa. The reason this happens I think is because the y value that gets checked between going left and right and up and down are different which you see with characterY = this.y + 15 at line 1591.

There are occasional flickers when the character changes frames but idk if that's even a code issue	

If you line up perfectly with the tip of a tree and then you stand right under an object like a log. You will get drawn underneath the log. This is because the code thinks ur behind object so it will draw you behind the tree and the log.

The cutoff for the isPersonInFrontMapObject is a lil off where if u stand jus near the tip of the tree it will think you're in front of it and not behind. Fixing this would mean moving the checking and entire tile higher which doesn't seem worth so I left it.

– Any other important info for me to play/mark your game
I hope you enjoy the cute lil game. I personally loved stardew valley, I first played it many years ago when my sister bought it for me. I will ask you in school tommorow but if you can give me like 1 day and mark my game late I will make the game a lil more interseting where instead of only buying parsnips you can buy other seeds too. It will be so much more fun that way but who knows if they'll be time for that its not bad already. :O

