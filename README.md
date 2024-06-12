To start the project run: 
´mvn spring-boot:run´

Controls for the project: 
Since this is a basic project, I have just added some commands to the main method. 
These commands are: 
start - Starts a game. with the input of a home and an away team. A match id is returned. 
finish - Finishes a game with the input of a match id. 
update - Updates the score of a game by inputting the match id, home score and away score. 
summary - Returns a list of actuve games. 

Important to note: 
- I have added some multi thread safety into the service class so that the scoreboard should stay consistent in the event of concurrency. 
- I have made it so that the scores can ony go up by one each update. I know VAR may take scores away, but I wanted to show another layer of validation that can be removed upon request. 

Features I would add in the future:
- a rest api to get and modify data easily
- potentially an open api yaml to keep the code consistent and for the automation code generation
- a timeline of events in matches. For example, a timeline might include: start time, score updates, half time scorea and full time score etc.
- a list of teams available by enum instead of a blank string value. This would add a layer of stability when entering teams into matches.

  
