//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException(String message) {
        super(message);
    }
}
