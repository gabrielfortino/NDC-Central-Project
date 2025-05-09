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
            case "23" :
                parseEncryptorInitialisationData(fields);
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

    private void parseEncryptorInitialisationData(String[] fields){
        System.out.println("Message Class : " +fields[0].charAt(0));
        System.out.println("Message Sub-Class : " + fields[0].charAt(1));
        System.out.println("LUNO : " + fields[1]);
        switch (fields[3]){
            case "1" :
                System.out.println("Information Identifier : EPP serial number and signature");
                System.out.println("EPP Serial Number : " + fields[4].substring(0,8));
                System.out.println("EPP Serial Number Signature : " + fields[4].substring(8,328));
            case "2" :
                System.out.println("Information Identifier : EPP public key and signature");
                System.out.println("EPP Public Key (PK-EPP) : " + fields[4].substring(0,320));
                System.out.println("EPP Public Key Signature : " + fields[4].substring(320,640));
            case "3" :
                System.out.println("Information Identifier : New Key Verification Value (KVV)");
                System.out.println("New KKV for key : " + fields[4]);
            case "4" :
                System.out.println("Information Identifier : Keys status");
                System.out.println("Master Key KVV : " + fields[4].substring(0,6));
                System.out.println("Communication Key KVV : " +fields[4].substring(6,12));
                System.out.println("MAC Key KVV : " + fields[4].substring(12,18));
                System.out.println("B Key KVV : " + fields[4].substring(18,24));
            case "5" :
                System.out.println("Information Identifier : Key loaded");
            case "6" :
                System.out.println("Information Identifier : Key entry mode");
                switch (fields[4]){
                    case "1" -> System.out.println("Single length without XOR");
                    case "2" -> System.out.println("Single length with XOR");
                    case "3" -> System.out.println("Double length wih XOR");
                    case "4" -> System.out.println("Double length, restricted");
                }
            case "7" :
                System.out.println("Information Identifier : RSA encryption KVV");
                System.out.println("Binary data length : " + fields[4]);
            case "8" :
                System.out.println("Information Identifier : SST certificate");
                System.out.println("Binary data length : " + fields[4]);
            case "9" :
                System.out.println("Information Identifier : SST random number");
                System.out.println("SST random number : " + fields[4]);
            case "A" :
                System.out.println("Information Identifier : PKCS7 key loaded");
                System.out.println("KVV of new DES key : " + fields[4].substring(0,6));
                System.out.println("Binary data length : " + fields[4].substring(6,9));
            case "B" :
                System.out.println("Information Identifier : Encryptor capabilities and state");
                switch (fields[4].substring(0,2)){
                    case "00" -> System.out.println("Remote Key Protocol : None");
                    case "01" -> System.out.println("Remote Key Protocol : Signature");
                    case "02" -> System.out.println("Remote Key Protocol : Certificate");
                    case "03" -> System.out.println("Remote Key Protocol : Signature and certificate");
                    case "04" -> System.out.println("Remote Key Protocol : Enhanced signature");
                    case "06" -> System.out.println("Remote Key Protocol : Enhanced signature and certificate");
                }
                switch (fields[4].substring(2,3)){
                    case "00" -> System.out.println("Cartificate state : Not ready or not supported");
                    case "01" -> System.out.println("Certificate state : Certificate primary");
                    case "02" -> System.out.println("Certificate state : Certificate secondary");
                }
                switch (fields[4].substring(4,6)){
                    case "0" -> System.out.println("Variable length EPP serial numbers not supported");
                    case "1" -> System.out.println("Variable length EPP serial numbers supported");
                }
            case "C" :
                System.out.println("Information Identifier : Key deleted");
            case "D" :
                System.out.println("Information Identifier : EPP attributes");
//                System.out.println("");
            case "E" :
                System.out.println("Information Identifier : Variable‐length EPP serial number and signature");
        }


    }

    private void parseEjData(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub-Class : " + subFields[1]);
        System.out.println("Machine Number : " + fields[1].substring(0,6));
        System.out.println("Date : " + fields[1].substring(6,12));
        System.out.println("Time : " + fields[1].substring(12,18));
        System.out.println("Last Char Previous Block : " + fields[1].substring(18,24));
        System.out.println("Last Char This Block : " + fields[1].substring(24,30));
        System.out.println("Block Length : " + fields[1].substring(30,33));

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
        String statusDescriptor = fields[3];
        String statusInformation = fields[4];
        switch (statusDescriptor) {
            case "8": // Device Fault
                parseDeviceFaultStatus(statusInformation);
                break;
            case "9": //Ready
                System.out.println("Status Information : Ready");
            case "B": // Ready
                parseReadyStatus(statusInformation);
                break;
            case "A": // Command Reject
                System.out.println("Status Information : Command Reject");
                break;
            case "C": // Specific Command Reject
                parseSpecificCommandRejectStatus(statusInformation);
                break;
            case "F": // Terminal State
                parseTerminalStateStatus(statusInformation);
                break;
        }
        System.out.println("Message Authentication Code (MAC) Data : " + fields[5]);
    }

    private void parseDeviceFaultStatus(String statusInformation) {
        String[] subStatusInformation = statusInformation.split("\u001C");
        System.out.println("Device Identifier Graphic : " + subStatusInformation[0].charAt(0));
        System.out.println("Transaction Status : " + subStatusInformation[0].substring(1,18));
        String errorSeverity = subStatusInformation[1];
        switch (errorSeverity){
            case "0" -> System.out.println("No Error");
            case "1" -> System.out.println("Routine");
            case "2" -> System.out.println("Warning");
            case "3" -> System.out.println("Suspend");
            case "4" -> System.out.println("Fatal");
        }
    }

    private void parseReadyStatus(String statusInformation) {
        String[] subStatusInformation = statusInformation.split("\u001C");
        System.out.println("Ready Status");
        System.out.println("Transaction Serial Number (TSN) :" + subStatusInformation[0]);
        if (subStatusInformation[1].equals("1")) {
            System.out.println("Data Identifier : Recycle Cassette Deposit Data");
        } else if (subStatusInformation[1].equals("2")) {
            System.out.println("Data Identifier : Recycle Cassette Dispense Data");
        } else {
            System.out.println("Data Identifier : -");
        }
    }

    private void parseSpecificCommandRejectStatus(String statusInformation) {
        System.out.println("Specific Command Reject Status");
        String statusValue = statusInformation.substring(0,1);
        String statusQualifier = statusInformation.substring(1,3);
        switch(statusValue){
            case "1" :
                System.out.println("Status Information : MAC Failure. Result of MAC verification did not equal the MAC field in the message.");
            case "2" :
                System.out.println("Status Information : Time Variant Number Failure. The time variant number received in the last Transaction Reply message is not the same as the last transmitted value.");
            case "3" :
                System.out.println("Security Terminal Number Mismatch. The number received in the last transaction reply security terminal number is not the same as the number held in the terminal.");
            case "A" :
                switch (statusQualifier) {
                    case "01" -> System.out.println("Message length error");
                    case "02" -> System.out.println("Field Separator missing/unexpectedly found.");
                    case "03" -> System.out.println("Transaction Reply message has too many print groups.");
                    case "04" -> System.out.println("Group Separator missing/unexpectedly found.");
                    case "07" -> System.out.println("Malformed XML.");
                    case "08" -> System.out.println("XML does not conform to XML schema.");
                }
            case "B" :
                switch (statusQualifier) {
                    case "01" -> System.out.println("Illegal Message Class.");
                    case "02" -> System.out.println("Illegal Message Sub‐Class or Identifier.");
                    case "03" -> System.out.println("Illegal Encryption Key Change or Extended Encryption Key Change Message Modifier.");
                    case "04" -> System.out.println("Illegal Terminal Command Code.");
                    case "05" -> System.out.println("Illegal Terminal Command Modifier");
                    case "06" -> System.out.println("Illegal Transaction Reply Function Identifier.");
                    case "07" -> System.out.println("Data field contains non‐decimal digit.");
                    case "08" -> System.out.println("Data field value out of range.");
                    case "09" -> System.out.println("Invalid Message Co‐Ordination number.");
                    case "10" -> System.out.println("Illegal FIT number.");
                    case "11" -> System.out.println("Too many notes in a dispense function.");
                    case "12" -> System.out.println("Reserved");
                    case "13" -> System.out.println("Unrecognised Document Destination.");
                    case "14" -> System.out.println("Reserved");
                    case "15" -> System.out.println("Unrecognised Buffer Identifier.");
                    case "16" -> System.out.println("Reserved");
                    case "17" -> System.out.println("Document Name Error.");
                    case "18" -> System.out.println("The screen identifier is out of range.");
                    case "19" -> System.out.println("Reserved");
                    case "20" -> System.out.println("No data supplied to endorse cheque.");
                    case "21" -> System.out.println("Reserved");
                    case "22" -> System.out.println("Invalid Encryption Key Size.");
                    case "23" -> System.out.println("RSA Signature Verification Failed.");
                    case "24" -> System.out.println("Signature or Encryption Key PKCS#1 Packing Failed.");
                    case "25" -> System.out.println("Signature or Encryption Key PKCS#1 Unpacking Failed.");
                    case "26" -> System.out.println("Invalid Signature or Encryption Key PKCS#1 Pad Block Type.");
                    case "27" -> System.out.println("Fixed Header Decryption Failed.");
                    case "28" -> System.out.println("Null Byte After Padding Missing.");
                    case "29" -> System.out.println("Invalid Pad Byte Count.");
                    case "34" -> System.out.println("Invalid/Incomplete Cheque Identifier(s).");
                    case "35" -> System.out.println("Passbook update not supported in specified Transaction Reply Function.");
                }
            case "C" :
                switch (statusQualifier){
                    case "01" -> System.out.println("Message type only accepted while terminal is In‐Service and expecting a Transaction Reply.");
                    case "02" -> System.out.println("Message not accepted while diagnostics is in progress. This is returned when the application has passed control to VDM.");
                    case "03" -> System.out.println("Message not accepted while in Out‐of‐Service or Supply mode.");
                    case "04" -> System.out.println("Message not accepted while in In‐Service mode.");
                    case "05" -> System.out.println("Message not allowed while configured for NCR status message mode.");
                    case "06", "07", "08", "09", "16", "12", "13", "14" -> System.out.println("Reserved");
                    case "10" -> System.out.println("Message not accepted while processing a Transaction Reply.");
                    case "11" -> System.out.println("Cheque not present in cheque processor transport while processing a Transaction Reply.");
                    case "15" -> System.out.println("Encryption Key Change or Extended Encryption Key Change message not accepted during a cardholder transaction, or while the terminal is in suspend mode, or while the operator is initiating the execution of supervisory/settlement transactions.");
                    case "17" -> System.out.println("Key change operation cannot be accepted in restricted encryption mode. This applies when an Extended Encryption Key Change message with modifier ‘3’, ‘4’, ‘6’ or ‘7’ is received in restricted mode.");
                    case "18" -> System.out.println("Key entry mode not authorised.");
                }
            case "D" :
                switch (statusQualifier){
                    case "01" -> System.out.println("Encryption failure during Encryption Key Change or Extended Encryption Key Change message.");
                    case "02" -> System.out.println("Time‐of‐Day Clock failure or invalid data sent during Date/Time Set command.");
                    case "03", "04", "05" -> System.out.println("Reserved");
                    case "06" -> System.out.println("Insufficient disk space.");
                    case "07" -> System.out.println("File IO error.");
                    case "08" -> System.out.println("File not found.");
                }
            case "E" :
                switch (statusQualifier){
                    case "01" -> System.out.println("A DLL required to complete the transaction reply processing is missing.");
                    case "02" -> System.out.println("Required device not configured. Also, sideways print on the receipt is requested, but either the printer does not have the capability or has not been configured for sideways printing.");
                    case "03", "04" -> System.out.println("Reserved");
                    case "05" -> System.out.println("Journal printer backup inactive.");
                }
        }
    }

    private void parseTerminalStateStatus(String statusInformation) {
        System.out.println("Terminal State Status");
        String[] subStatusInformation = statusInformation.split("\u001C");
        String statusValue = subStatusInformation[0].substring(0,1);
        switch (statusValue){
            case "1" : //send configuration information
                System.out.println("Message Identifier : " + statusValue);
                System.out.println("Configuration ID : " + subStatusInformation[0].substring(1,5));
                System.out.println("Hardware Fitness : " + subStatusInformation[1]);
                System.out.println("Hardware Configuration : " + subStatusInformation[2]);
                System.out.println("Supplier Status : " + subStatusInformation[3]);
                System.out.println("Sensor Status : " + subStatusInformation[4]);
                System.out.println("Advanced NDC Release Number : " + subStatusInformation[5]);
                System.out.println("Advanced NDC Software ID : " + subStatusInformation[6]);
            case "2" : //send supply counters
                System.out.println("Message Identifier : " + statusValue);
                System.out.println("Transaction Serial Number (TSN) : " + subStatusInformation[0].substring(1,5));
                System.out.println("Accumulated Transaction Count : " + subStatusInformation[0].substring(5,12));
                System.out.println("Notes In Cassettes : " + subStatusInformation[0].substring(12,32));
                System.out.println("Notes Rejected : " + subStatusInformation[0].substring(32,52));
                System.out.println("Notes Dispensed : " + subStatusInformation[0].substring(52,72));
                System.out.println("Last Transaction Notes Dispensed : " + subStatusInformation[0].substring(72,92));
                System.out.println("Cards Captured : " + subStatusInformation[0].substring(92,97));
                System.out.println("Envelopes Deposited : " + subStatusInformation[0].substring(97,102));
                System.out.println("Camera Film Remaining : " + subStatusInformation[0].substring(102,107));
                System.out.println("Last Envelope Serial Number : " + subStatusInformation[0].substring(107,112));
            case "3" : //send tally information (unsupported)
                System.out.println("Message Identifier : " +statusValue);
                System.out.println("Group Number : " + subStatusInformation[0].charAt(1));
                System.out.println("Year : " + subStatusInformation[0].substring(1,3));
                System.out.println("Month : " + subStatusInformation[0].substring(3,5));
                System.out.println("Day : " + subStatusInformation[0].substring(5,7));
                System.out.println("Hour : " + subStatusInformation[0].substring(7,9));
                System.out.println("Minute : " + subStatusInformation[0].substring(9,11));
                System.out.println("Second : " + subStatusInformation[0].substring(11,13));
                System.out.println("Tally Data : " + subStatusInformation[0].substring(13,19));
            case "4" : //send error log information (unsupported)
                System.out.println("Message Identifier : " +statusValue);
                System.out.println("Group Number : " +subStatusInformation[0].charAt(1));
                System.out.println("New Entries : " + subStatusInformation[0].substring(1,3));
                System.out.println("Year : " + subStatusInformation[0].substring(3,5));
                System.out.println("Month : " + subStatusInformation[0].substring(5,7));
                System.out.println("Day : " + subStatusInformation[0].substring(7,9));
                System.out.println("Hour : " + subStatusInformation[0].substring(9,11));
                System.out.println("Minute : " + subStatusInformation[0].substring(11,13));
                System.out.println("Second : " + subStatusInformation[0].substring(13,15));
            case "5" : //send date/time information
                System.out.println("Message Identifier :" +statusValue);
                String ToDClockStatus = subStatusInformation[0].substring(1,2);
                switch (ToDClockStatus){
                    case "0" -> System.out.println("Time is actual");
                    case "1" -> System.out.println("Time is default");
                    case "2" -> System.out.println("ToD malfunction");
                }
                System.out.println("Year : " +subStatusInformation[0].substring(2,4));
                System.out.println("Month : " + subStatusInformation[0].substring(4,6));
                System.out.println("Day : " + subStatusInformation[0].substring(6,8));
                System.out.println("Hour : " + subStatusInformation[0].substring(8,10));
                System.out.println("Minute : " + subStatusInformation[0].substring(10,12));
                System.out.println("Second : " + subStatusInformation[0].substring(12,14));
            case "6" : //send configuration ID
                System.out.println("Message Identifier :" +statusValue);
                System.out.println("Configuration ID : " + subStatusInformation[0].substring(1,5));
            case "F" : //EKC retrieve hallmark key (unsupported)
            case "H" : //Hardware configuration data
                System.out.println("Message Identifier : " + statusValue);
                System.out.println("Configuration ID Identifier : " +subStatusInformation[0].charAt(1));
                System.out.println("Configuration ID : " + subStatusInformation[0].substring(2,6));
                System.out.println("Product Class : " +subStatusInformation[1]);
                System.out.println();
            case "I" : //Supplies data
                System.out.println("Message Identifier :");
            case "J" : //Fitness data
                System.out.println("Message Identifier : " + statusValue);
                System.out.println("Hardware Fitness Identifier : " + subStatusInformation[0].charAt(1));
            case "K" : //tamper and sensor status data
                System.out.println("Message Identifier : " +statusValue);
            case "L" : //software ID and release number data
                System.out.println("Message Identifier : " +statusValue);
            case "M" : //local configuration option digits
                System.out.println("Message Identifier : " +statusValue);
            case "N" : //send note definitions (BNA)

        }
    }


    private void parseUnsolicitedStatus(String[] fields) {
        String[] subFields = fields[0].split("");
        System.out.println("Message Class : " + subFields[0]);
        System.out.println("Message Sub Class : " + subFields[1]);
        System.out.println("LUNO : " + fields[1]);
        System.out.println("Status Information : " + fields[2]);
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