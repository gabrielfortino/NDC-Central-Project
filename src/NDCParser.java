
import java.nio.charset.StandardCharsets;

public class NDCParser {
    public void messageParser(byte[] messageBytes){
        String message = new String(messageBytes, StandardCharsets.UTF_8);

        // Split berdasarkan karakter Field Separator (FS = \x1C)
        String[] fields = message.split("\u001C");

        switch (fields[0]){
            case "11" :
                parseTransactionRequest(fields);
//                break;
            case "12" :
                parseUnsolicitedStatus(fields);
//                break;
            case "22" :
                parseSolicitedStatus(fields);
//                break;
            case "3" :
                parseAlertMessage(fields);
//                break;
            case "41":
                //Software Management Status Message(NDC+ Only)
                parseSoftwareManagement(fields);
//                break;
            case "51":
                parseExitToHost(fields);
//                break;
            case "61":
                parseEjData(fields);
//                break;
            case "1":
                parseTerminalCommands(fields);
//                break;
        }
    }

    private void parseEjData(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub-Class : " + subFields[1]);
        System.out.println("Machine Number : " + fields[1]);
    }

    private void parseExitToHost(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub-Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Data Supplied by The Exit : " + fields[2]);
    }

    private void parseSoftwareManagement(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub-Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("SM Installation Activity : " + fields[2]);
    }

    private void parseAlertMessage(String[] fields) {

    }

    private void parseSolicitedStatus(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Time Variant Number : " + fields[2]);
        System.out.println("Status Descriptor : " + fields[3]);
        System.out.println("Status Information : " + fields[4]);
        System.out.println("Message Authentication Code (MAC) Data : " + fields[5]);
    }

    private void parseUnsolicitedStatus(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Status Information : " + fields[2]);
    }

    private String getDescriptor(String field) {

        switch (field){
            case "8": return field + "-DeviceFault";
            case "9": return field + "-Ready";
            case "A": return field + "-CommandReject";
            case "B": return field + "-Ready";
            case "C": return field + "-SpesificCommandReject";
            case "F": return field + "-TerminalState";
            default:
                throw new IllegalStateException("Unexpected value: " + field);
        }
    }

    private void parseTransactionRequest(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Time Variant Number : " + fields[2]);
        String[] subFields1 = fields[3].split("");
        System.out.println("Top of Receipt Transaction Flag : " + subFields1[0]);
        System.out.println("Message Co-Ordination Number : " + subFields1[1]);
        System.out.println("Track 2 Data : " + fields[4]);
        System.out.println("Track 3 Data : " + fields[5]);
        System.out.println("Operation Code Data : " + fields[6]);
        System.out.println("Amount Entry Field : " + fields[7]);
        System.out.println("PIN Buffer : " + fields[8]);
        System.out.println("General Purpose Buffer : " + fields[9]);
        System.out.println("General Purpose Buffer : " + fields[10]);
    }
    private void parseTerminalCommands(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Response Flag : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Message Sequence Number :" + fields[2]);
        String[] subFields1 = fields[3].split("");
        System.out.println("Command Code : " + subFields1[0]);
        System.out.println("Command Modifier : " + subFields1[1]);
        System.out.println("Protocol Trailer : " + subFields1[2]);
    }
}