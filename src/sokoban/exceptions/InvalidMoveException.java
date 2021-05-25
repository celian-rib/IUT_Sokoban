//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.exceptions;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}
