public class MessageSender {

    // Function to send Initialization Message
    public static byte[] requestConfigIDMessage() {
        // Example initialization message
        return new byte[]{0x31, 0x1C, 0x33, 0x30, 0x30, 0x1C, 0x1C, 0x33, 0x00};
    }

    // Function to send Cardholder Data Message
    public static byte[] configurationParameterLoadMessage() {
        return new byte[]{
                0x33, 0x1C, 0x33, 0x30, 0x30, 0x1C, 0x1C, 0x31, 0x33, 0x1C, 0x00
        };
//        0x34, 0x37, 0x33, 0x33, 0x30, 0x30, 0x31, 0x33, 0x31, 0x30, 0x30, 0x30, 0x30,
//                0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
//                0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x33, 0x30, 0x30, 0x30, 0x37, 0x30,
//                0x35, 0x30
//        };
//                0x02,
//                0x33, // Message Class '3'
//                0x1C, // Field Separator
//                0x1C, // Field Separator (for empty LUNO/Seq No.)
//                0x1C, // Field Separator
//                0x31, // Message Sub-Class '1'
//                0x33, // Message Identifier '3'
//                0x1C, // Field Separator
//                0x31, 0x32, 0x33, // Data: LUNO to set ('123')
//                0x1C, // Field Separator
//                0x30, 0x30, // Data: Timer Number '00'
//                0x30, 0x32, 0x35, // Data: Timer Value '025' (25 * 800ms = 20 seconds)
//                0x00
    }
    public static byte[] stateTableLoadMessage(){
        return new byte[]{
                
        };
    }

    // Add more functions here as needed
}
