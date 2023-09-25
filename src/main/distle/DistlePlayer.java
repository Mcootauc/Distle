package main.distle;

import static main.distle.EditDistanceUtils.*;
import java.util.*;

/**
 * AI Distle Player! Contains all logic used to automagically play the game of
 * Distle with frightening accuracy (hopefully).
 */
public class DistlePlayer {
	Set<String> theDict;
    
    /**
     * Constructs a new DistlePlayer.
     */
    public DistlePlayer () {

    }
    
    /**
     * Called at the start of every new game of Distle, and parameterized by the
     * dictionary composing all possible words that can be used as guesses / one of
     * which is the correct.
     * 
     * @param dictionary The dictionary from which the correct answer and guesses
     * can be drawn.
     * @param maxGuesses The max number of guesses available to the player.
     */
    public void startNewGame (Set<String> dictionary, int maxGuesses) {
    	this.theDict = dictionary;
    }
    
    /**
     * Requests a new guess to be made in the current game of Distle. Uses the
     * DistlePlayer's fields to arrive at this decision.
     * 
     * @return The next guess from this DistlePlayer.
     */
    public String makeGuess () {
    	Iterator<String> iter = this.theDict.iterator();
    	return iter.next().toString();
    }
    
    /**
     * Called by the DistleGame after the DistlePlayer has made an incorrect guess. The
     * feedback furnished is as follows:
     * <ul>
     *   <li>guess, the player's incorrect guess (repeated here for convenience)</li>
     *   <li>editDistance, the numerical edit distance between the guess and secret word</li>
     *   <li>transforms, a list of top-down transforms needed to turn the guess into the secret word</li>
     * </ul>
     * @param guess The last, incorrect, guess made by the DistlePlayer
     * @param editDistance Numerical distance between the guess and the secret word
     * @param transforms List of top-down transforms needed to turn the guess into the secret word
     */
    public void getFeedback (String guess, int editDistance, List<String> transforms) {
        Iterator<String> itr = this.theDict.iterator();
        while(itr.hasNext()) {
        	String newGuess = itr.next();
        	if (!getTransformationList(guess, newGuess).equals(transforms)) {
        		itr.remove();
        	}
        }
    }
    
}
