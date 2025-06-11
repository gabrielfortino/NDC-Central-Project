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
        String[] StatusInformationField = fields[2].split("\u001C");
        String deviceIdentifierGraphic = StatusInformationField[0].substring(0,1);
        String deviceStatus = StatusInformationField[0].substring(1,2);
        String errorSeverity = StatusInformationField[1];
        String diagnosticStatus = StatusInformationField[2];
        String suppliesStatus = StatusInformationField[3];
        switch (deviceIdentifierGraphic){
            case "A" :
                System.out.println("Time of Day Clock Status");
                System.out.println("Device Identifier Graphic : " + StatusInformationField[0].charAt(0));
                switch (deviceStatus){
                    case "1" :
                        System.out.println("Device Status : (" + deviceStatus + ") Clock reset but running");
                    case "2" :
                        System.out.println("Device Status : (" + deviceStatus + ") Clock has stopped");
                }
                switch (errorSeverity){
                    case "2" :
                        System.out.println("Error Severity : (" + errorSeverity + ") Warning");
                    case "4" :
                        System.out.println("Error Severity : (" + errorSeverity + ") Fatal");
                }
            case "B" :
                System.out.println("Power Failure Status");
                System.out.println("Device Identifier : " + deviceIdentifierGraphic);
                System.out.println("Config ID : " +StatusInformationField[0].substring(1,5));
            case "D" :
                System.out.println("Card Reader/Writer Status");
                System.out.println("Device Identifier : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " :  No transaction exception condition occurred but consult other fields for error severity, diagnostic status or supplies status changes.");
                    case "1" :
                        System.out.println(deviceStatus + " : The cardholder did not take his card within the allowed time and it was captured or jammed.");
                    case "2" :
                        System.out.println(deviceStatus + " : The mechanism failed to eject the card, which was either captured or jammed.");
                    case "3" :
                        System.out.println(deviceStatus + " : The mechanism failed to update the requested tracks on the card.");
                    case "4" :
                        System.out.println(deviceStatus + " : Invalid track data received from Central.");
                    case "7" :
                        System.out.println(deviceStatus + " : Error in track data.");
                }
                switch (errorSeverity){
                    case "2" :
                        System.out.println("Error Severity : (" + errorSeverity + ") Warning");
                    case "4" :
                        System.out.println("Error Severity : (" + errorSeverity + ") Fatal");
                }
                System.out.println("Diagnostic Status : " + diagnosticStatus);
                switch (suppliesStatus){
                    case "0" :
                        System.out.println(suppliesStatus + " : No new state");
                    case "1" :
                        System.out.println(suppliesStatus + " : No overfill condition (capture bin)");
                    case "4" :
                        System.out.println(suppliesStatus + " : Overfill condition (capture bin)");
                }
            case "E" :
                System.out.println("Cash Handler");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : Successful operation, but an exception has occured as detailed in subsequent fields");
                    case "1" :
                        System.out.println(deviceStatus + " : Short dispense. For a spray dispenser, this can also indicate that an extra note has been dispensed.");
                    case "2" :
                        System.out.println(deviceStatus + " : No notes dispensed");
                    case "3" :
                        System.out.println(deviceStatus + " : Notes dispensed unknown. The cardholder may have had access to any presented notes, so it should be assumed some may have been dispensed. Intervention may be required to reconcile the cash amount totals. The following counts contain requested dispense values.");
                    case "4" :
                        System.out.println(deviceStatus + " : No notes dispensed or card not ejected. This status is returned on a card before cash transaction if the stack operation fails and the notes are purged prior to card eject.");
                    case "5" :
                        System.out.println(deviceStatus + " : Some notes have been retracted when the notes were not taken following a Present time‐out. The number of notes retracted is unknown");
                }


            case "F" :
                System.out.println("Depository");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : Successful operation, but an exception has occurred as detailed in subsequent fields.");
                    case "1" :
                        System.out.println(deviceStatus + " : Time-out on cardholder deposit.");
                    case "2" :
                        System.out.println(deviceStatus + " : Failure to enable mechanism for a deposit.");
                    case "3" :
                        System.out.println(deviceStatus + " : Envelope/document jam or envelope/document deposit failed. The cardholder has access. This status is also returned if there is any doubt about cardholder access.");
                    case "4" :
                        System.out.println(deviceStatus + " : Envelope/document jam or envelope/document deposit failed. The cardholder does not have access.");
                }
                System.out.println("Error Severity : " + StatusInformationField[1]);
                System.out.println("Diagnostic Status : " + StatusInformationField[2]);
                switch (suppliesStatus){
                    case "0" :
                        System.out.println(suppliesStatus + " : No envelope deposited");
                    case "1" :
                        System.out.println(suppliesStatus + " : No overfill condition");
                    case "4" :
                        System.out.println(suppliesStatus + " : Overfill detected");
                }
            case "G" :
                System.out.println("Receipt Printer");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : Successful print");
                    case "1" :
                        System.out.println(deviceStatus + " : Print operation not successfully completed");
                    case "2" :
                        System.out.println(deviceStatus + " : Device not configured");
                    case "4" :
                        System.out.println(deviceStatus + " : Cancel key pressed during sideways receipt print");
                    case "5" :
                        System.out.println(deviceStatus + " : Receipt retracted");
                }
                switch (errorSeverity){
                    case "1" :
                        System.out.println("Receipt Printer, core component");
                    case "2" :
                        System.out.println("Capture Bin");
                }
                System.out.println("Diagnostic Status : " + StatusInformationField[2]);
                switch (suppliesStatus.substring(0,1)){
                    case "1" :
                        System.out.println(suppliesStatus.charAt(0) + " : Sufficient paper");
                    case "2" :
                        System.out.println(suppliesStatus.charAt(0) + " : Paper low");
                    case "3" :
                        System.out.println(suppliesStatus.charAt(0) + " : Paper exhausted");
                }
                switch (suppliesStatus.substring(1,2)){
                    case "1" :
                        System.out.println(suppliesStatus.charAt(1) + " : Ribbon OK");
                    case "2" :
                        System.out.println(suppliesStatus.charAt(1) + " : Ribbon replacement recommended");
                    case "3" :
                        System.out.println(suppliesStatus.charAt(1) + " : Ribbon replacemenet mandatory");
                }
                switch (suppliesStatus.substring(2,3)){
                    case "1" :
                        System.out.println(suppliesStatus.charAt(2) + " : Print-head OK");
                    case "2" :
                        System.out.println(suppliesStatus.charAt(2) + " : Print-head replacement recommended");
                    case "3" :
                        System.out.println(suppliesStatus.charAt(2) + " : Print-head replacement mandatory");
                }
                switch (suppliesStatus.substring(3,4)){
                    case "1" :
                        System.out.println(suppliesStatus.charAt(3) + " : Knife OK");
                    case "2" :
                        System.out.println(suppliesStatus.charAt(3) + " : Knife replacement recommended");
                }
                switch (suppliesStatus.substring(4,5)){
                    case "1" :
                        System.out.println(suppliesStatus.charAt(4) + " : Capture bin OK");
                    case "4" :
                        System.out.println(suppliesStatus.charAt(4) + " : Capture bin overfill");
                }
                
            case "H" :
                System.out.println("Electronic Journal Printer Status");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : Successful print");
                    case "1" :
                        System.out.println(deviceStatus + " : Print operation not successfully completed");
                    case "2" :
                        System.out.println(deviceStatus + " : Device not configured");
                    case "6" :
                        System.out.println(deviceStatus + " : Journal printer backup activated");
                    case "7" :
                        System.out.println(deviceStatus + " : Journal printer backup and reprint terminated");
                    case "8" :
                        System.out.println(deviceStatus + " : Journal printer backup reprint started");
                    case "9" :
                        System.out.println(deviceStatus + " : Journal printer backup halted");
                    case ":" :
                        System.out.println(deviceStatus + " : Journal printer backup log security error");
                    case ";" :
                        System.out.println(deviceStatus + " : Journal printer backup reprint halted");
                    case "<" :
                        System.out.println(deviceStatus + " : Journal printer backup tamper state entered");
                    case "=" :
                        System.out.println(deviceStatus + " : EJ in dual mode print operation successful");
                    case ">" :
                        System.out.println(deviceStatus + " : EJ in dual mode print operation not successful");
                }
                switch (errorSeverity){
                    case "0":
                        System.out.println("No error / OK");

                    case "2":
                        System.out.println("Warning");

                    case "4":
                        System.out.println("Fatal");
                }
                System.out.println("Diagnostic Status : " + diagnosticStatus);
                System.out.println("Supplies Status : " + suppliesStatus);
                String paperStatus = suppliesStatus.substring(0,1);
                String ribbonStatus = suppliesStatus.substring(1,2);
                String printHeadStatus = suppliesStatus.substring(2,3);
                String knifeStatus = suppliesStatus.substring(3,4);
                System.out.println("Supplies Status Information :");
                switch (paperStatus){
                    case "1" :
                        System.out.println(paperStatus + " : Sufficient paper");
                    case "2" :
                        System.out.println(paperStatus + " : Paper low");
                    case "3" :
                        System.out.println(paperStatus + " : Paper exhausted");
                }
                switch (ribbonStatus){
                    case "1" :
                        System.out.println(ribbonStatus + " : Ribbon OK");
                    case "2" :
                        System.out.println(ribbonStatus + " : Ribbon replacement recommended");
                    case "3" :
                        System.out.println(ribbonStatus + " : Ribbon replacement mandatory");
                }
                switch (printHeadStatus){
                    case "1" :
                        System.out.println(printHeadStatus + " : Print-head OK");
                    case "2" :
                        System.out.println(printHeadStatus + " : Print-head replacement recommended");
                    case "3" :
                        System.out.println(printHeadStatus + " : Print-head replacement mandatory");
                }
                if (knifeStatus.equals("1")) {
                    System.out.println(knifeStatus + " : Knife OK");
                }
            case "K" :
                System.out.println("Night Safe Depository Status");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + ": Tenth consecutive 'customer did not attempt a deposit'. Reported only once");
                    case "1" :
                        System.out.println(deviceStatus + " : Undetected deposit, or bag detection switch blocked before enable");
                }
                switch (errorSeverity){
                    case "0" :
                        System.out.println("No error. Bag detection mechanism was clear when the deposit door was unlocked");
                    case "2" :
                        System.out.println("Warning. Bag detection mechanism was blocked when the deposit door was unlocked");
                }
                System.out.println("Diagnostic Status : " + diagnosticStatus);
                switch (suppliesStatus){
                    case "0" :
                        System.out.println(suppliesStatus + " : No new state");
                    case "1" :
                        System.out.println(suppliesStatus + " : No overfill condition");
                    case "4" :
                        System.out.println(suppliesStatus + " : Overfill condition");
                }
            case "L" :
                System.out.println("Encryptor");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "1" :
                        System.out.println(deviceStatus + " : Encryptor error");
                    case "2" :
                        System.out.println(deviceStatus + " : Encryptor not configured");
                }
                switch (errorSeverity){
                    case "0" :
                        System.out.println("No error");
                    case "2" :
                        System.out.println("Warning");
                    case "4" :
                        System.out.println("Fatal");
                }
                System.out.println("Diagnostic Status : " + diagnosticStatus);
            case "M" :
                System.out.println("Camera");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                System.out.println("Device Status : " + deviceStatus);
                switch (errorSeverity){
                    case "0" :
                        System.out.println("No error");
                    case "2" :
                        System.out.println("Warning");
                    case "4" :
                        System.out.println("Fatal");
                }
                System.out.println("Diagnostic Status : " + diagnosticStatus);
                switch (suppliesStatus){
                    case "1" :
                        System.out.println(suppliesStatus + " : Capacity OK");
                    case "2" :
                        System.out.println(suppliesStatus + " : Nearly Full");
                    case "3" :
                        System.out.println(suppliesStatus + " : Capacity Exhausted");
                }
            case "P" :
                System.out.println("Sensors");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "1" :
                        System.out.println(deviceStatus + " : IT sensor change");
                        switch (StatusInformationField[0].substring(2,3)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(2) + " : Supervisor mode inactive");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(2) + " : Supervisor mode active");
                        }
                        switch (StatusInformationField[0].substring(3,4)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(3) + " : Vibration and/or heat sensor inactive");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(3) + " : Vibration and/or heat sensor active");
                        }
                        switch (StatusInformationField[0].substring(4,5)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(4) + " : Door contact sensor inactive");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(4) + " : Door contact sensor active");
                        }
                        switch (StatusInformationField[0].substring(5,6)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(5) + " : Silent signal sensor inactive");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(5) + " : Silent signal sensor active");
                        }
                        switch (StatusInformationField[0].substring(6,7)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(6) + " : Electronics enclosure sensor inactive");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(6) + " : Electronics enclosure sensor active");
                        }
                        switch (StatusInformationField[0].substring(7,8)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(7) + " : Deposit bin out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(7) + " : Deposit bin in");
                        }
                        switch (StatusInformationField[0].substring(8,9)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(8) + " : Card bin out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(8) + " : Card bin in");
                        }
                        switch (StatusInformationField[0].substring(9,10)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(9) + " : Currency reject bin out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(9) + " : Currency reject bin in");
                        }
                        switch (StatusInformationField[0].substring(10,11)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(10) + " : Currency cassette in position 1 (top) out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(10) + " : Currency cassette in position 1 (top) in");
                        }
                        switch (StatusInformationField[0].substring(11,12)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(11) + " : Currency cassette in position 2 (second) out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(11) + " : Currency cassette in position 2 (second) in");
                        }
                        switch (StatusInformationField[0].substring(12,13)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(12) + " : Currency cassette in position 3 (third) out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(12) + " : Currency cassette in position 3 (third) in");
                        }
                        switch (StatusInformationField[0].substring(13,14)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(13) + " : Currency cassette in position 4 (bottom) out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(13) + " : Currency cassette in position 4 (bottom) in");
                        }
                        switch (StatusInformationField[0].substring(14,15)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(14) + " : Coin dispenser out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(14) + " : Coin dispenser in");
                        }
                        switch (StatusInformationField[0].substring(15,16)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(15) + " : Coin dispenser hopper 1 out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(15) + " : Coin dispenser hopper 1 in");
                        }
                        switch (StatusInformationField[0].substring(16,17)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(16) + " : Coin dispenser hopper 2 out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(16) + " : Coin dispenser hopper 2 in");
                        }
                        switch (StatusInformationField[0].substring(17,18)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(17) + " : Coin dispenser hopper 3 out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(17) + " : Coin dispenser hopper 3 in");
                        }
                        switch (StatusInformationField[0].substring(18,19)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(18) + " : Coin dispenser hopper 4 out");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(18) + " : Coin dispenser hopper 4 in");
                        }
                        switch (StatusInformationField[0].substring(19,20)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(19) + " : CPM pockets open");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(19) + " : CPM pockets closed");
                        }
                    case "2" :
                        System.out.println(deviceStatus + " : Mode change");
                        switch (StatusInformationField[0].substring(2,3)){
                            case "0" :
                                System.out.println(StatusInformationField[0].charAt(2) + " : Supervisor mode exit");
                            case "1" :
                                System.out.println(StatusInformationField[0].charAt(2) + " : Supervisor mode entry");
                        }
                        if (StatusInformationField[0].substring(3,4) != null || StatusInformationField[0].substring(3,4) != "0"){
                            System.out.println(StatusInformationField[0].charAt(3) + " : Simulated Supervisor mode entry/exit during AER (if configured through the registry)");
                        }
                    case "3" :
                        System.out.println(deviceStatus + " : Alarm state change");
                    case "5" :
                        System.out.println(deviceStatus + " : Full TI and full alarms change detected");
                    case "6" :
                        System.out.println(deviceStatus + " : Flexible TI and alarms change detected");
                }
            case "Q" :
                System.out.println("Touch Screen Keyboard");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                if (StatusInformationField[0].charAt(1) == '3') {
                    System.out.println("Device Status (" + StatusInformationField[0].charAt(1) + ") : Hardware error ");
                }else{
                    System.out.println("Device Status : Normal");
                }
                if(StatusInformationField[1] == "4"){
                    System.out.println("Error Severity ( " +StatusInformationField[1] + ") : Fatal" );
                }else {
                    System.out.println("Error Severity : Normal");
                }
                System.out.println("Diagnostic Status : " + StatusInformationField[2]);
            case "R" :
                System.out.println("Supervisor Keys Status");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);

                String optionDigitValue = "1";

                String deviceStatusFull = "";
                int expectedCharsForE2 = 0;

                if (optionDigitValue.equals("0")){
                    expectedCharsForE2 = 2;
                }else if (optionDigitValue.equals("1")){
                    expectedCharsForE2 = 3;
                }else if (optionDigitValue.equals("2") || (optionDigitValue.compareTo("3") > 0 && optionDigitValue.matches("\\d+"))) {
                    expectedCharsForE2 = 5;
                }else if (optionDigitValue.equals("3")) {
                    expectedCharsForE2 = 7;
                }

                if(expectedCharsForE2 > 0){
                    if (StatusInformationField != null && StatusInformationField.length >= expectedCharsForE2) {
                        StringBuilder sb_e2 = new StringBuilder();


                    }
                }
            case "S" :
                System.out.println("Cardholder Display Alarm");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                System.out.println("Device Status : " + StatusInformationField[0].charAt(1));
                switch (StatusInformationField[1]){
                    case "0" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : No error/not supported");
                    case "4" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : Fatal");
                }
            case "V" :
                System.out.println("Statement Printer");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : No transaction error condition");
                    case "1" :
                        System.out.println(deviceStatus + " : Print/cut not successful");
                    case "2" :
                        System.out.println(deviceStatus + " : Device not configured");
                    case "3" :
                        System.out.println(deviceStatus + " : Statement present in transport");
                    case "4" :
                        System.out.println(deviceStatus + " : Cardholder pressed Cancel during a 'print statement and wait' function");
                }
                switch (errorSeverity){
                    case "0" :
                        System.out.println("Error Severity (" + errorSeverity + ") : No error/not supported");
                    case "1" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Routine error");
                    case "2" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Warning");
                    case "3" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Suspend");
                    case "4" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Fatal");
                }
                System.out.println("Diagnostic Status : " + StatusInformationField[2]);
                String printerPaperStatusV = StatusInformationField[3].substring(0,1);
                String paperRibbonStatusV = StatusInformationField[3].substring(1,2);
                String printHeadStatusV = StatusInformationField[3].substring(2,3);
                String knifeStatusV = StatusInformationField[3].substring(3,4);
                String captureBinStatusV = StatusInformationField[3].substring(4,5);

                switch (printerPaperStatusV){
                    case "1" :
                        System.out.println("Printer Paper Status (" + printerPaperStatusV + ") : Sufficient paper");
                    case "2" :
                        System.out.println("Printer Paper Status (" + printerPaperStatusV + ") : Paper low");
                    case "3" :
                        System.out.println("Printer Paper Status (" + printerPaperStatusV + ") : Paper exhausted");
                }
                switch (paperRibbonStatusV){
                    case "1" :
                        System.out.println("Paper Ribbon Status (" + paperRibbonStatusV + ") : Ribbon OK");
                    case "2" :
                        System.out.println("Paper Ribbon Status (" + paperRibbonStatusV + ") : Ribbon replacement recommended");
                    case "3" :
                        System.out.println("Paper Ribbon Status (" + paperRibbonStatusV + ") : Ribbon replacement mandatory");
                }
                switch (printHeadStatusV){
                    case "1" :
                        System.out.println("Print Head Status (" + printHeadStatusV + ") : Print-head OK");
                    case "2" :
                        System.out.println("Print Head Status (" + printHeadStatusV + ") : Print-head replacement recommended");
                    case "3" :
                        System.out.println("Print Head Status (" + printHeadStatusV+ ") : Print-head replacement mandatory");
                }
                switch (knifeStatusV){
                    case "1" :
                        System.out.println("Knife Status (" + knifeStatusV + ") : Knife OK");
                    case "2" :
                        System.out.println("Knife Status (" + knifeStatusV+ ") : Knife replacement recommended");
                    case "3" :
                        System.out.println("Knife Status (" + knifeStatusV + ") : Knife replacement mandatory");
                }
                switch (captureBinStatusV){
                    case "1" :
                        System.out.println("Capture Bin Status (" + captureBinStatusV + ") : Capture bin OK");
                    case "4" :
                        System.out.println("Capture Bin Status (" + captureBinStatusV + ") : Capture bin overfill");
                }
            case "a" :
                System.out.println("Voice Guidance Status");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                if (deviceStatus.equals("1")){
                    System.out.println("Device Status (" + deviceStatus + ") : An error has occurred");
                }else {
                    System.out.println("Device Status : Normal");
                }
                switch (errorSeverity){
                    case "0" :
                        System.out.println("Error Severity (" + errorSeverity + ") : No error");
                    case "1" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Routine error");
                    case "2" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Warning");
                    case "3" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Suspend");
                    case "4" :
                        System.out.println("Error Severity (" + errorSeverity + ") : Fatal");
                }
                String voiceDiagnosticStatus1 = diagnosticStatus.substring(0,2);
                String voiceDiagnosticStatus2 = diagnosticStatus.substring(2,4);
                String voiceDiagnosticStatus3 = diagnosticStatus.substring(4,6);
                String voiceDiagnosticStatus4 = diagnosticStatus.substring(6,8);
                switch (voiceDiagnosticStatus1){
                    case "00" :
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus1 + ") : Audio card is inaccessible");
                    case "01" :
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus1 + ") : Audio card is accessible");
                }
                switch (voiceDiagnosticStatus2){
                    case "00" :
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : No audio jack is available");
                    case "01" :
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in manual mode and the public state. All audio messages are played through the speakers");
                    case "02":
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in automatic mode and the public state. When a headset is inserted, the audio messages are played through the audio jack; otherwise audio messages are played through the speakers.");
                    case "04":
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in semi-automatic mode and the public state. When a headset is inserted, the audio messages are played through the audio jack; otherwise audio messages are played through the speakers.");
                    case "08":
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in manual mode and the private state. All audio messages are played through the audio jack only, whether or not a headset is inserted.");
                    case "16":
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in automatic mode and the private state. When a headset is inserted, audio messages are played through the audio jack; when the headset is removed, the device enters the public state.");
                    case "32":
                        System.out.println("Diagnostic Status (" + voiceDiagnosticStatus2 + ") : The audio system is in semi-automatic mode and the private state. All audio messages are played through the audio jack; when the headset is removed, the audio system remains in the private state.");
                }
                switch (voiceDiagnosticStatus3){
                    case "00":
                        System.out.println("Audio Status (" + voiceDiagnosticStatus3 + ") : No audio jack is present");
                        break;
                    case "01":
                        System.out.println("Audio Status (" + voiceDiagnosticStatus3 + ") : A headset is connected");
                        break;
                    case "02":
                        System.out.println("Audio Status (" + voiceDiagnosticStatus3 + ") : No headset is connected");
                }
                switch (voiceDiagnosticStatus4){
                    case "00":
                        System.out.println("Audio Status (" + voiceDiagnosticStatus4 + ") : The XML definition file is not accessible");
                        break;
                    case "01":
                        System.out.println("Audio Status (" + voiceDiagnosticStatus4 + ") : The XML definition file is accessible");
                }
            case "w" :
                System.out.println("Bunch Note Acceptor Status");
                System.out.println("Device Identifier : " + deviceIdentifierGraphic); // Asumsikan deviceIdentifierGraphic berisi 'w'

                int fieldIndex = 0;

                String e2_transactionDeviceStatusCode = StatusInformationField[fieldIndex++];
                System.out.print("Transaction/Device Status (" + e2_transactionDeviceStatusCode + ") : ");
                switch (e2_transactionDeviceStatusCode) {
                    case "0":
                        System.out.println(" Successful operation, but an exception has occurred or notes have been moved in the device outside a Transaction Reply function. Up to date counts are included, which will be in the escrow notes field (refundable deposits) or the vaulted notes field (direct deposits). In this case, both counts are cumulative within the transaction.");

                    case "1":
                        System.out.println(" Cancel selected, Refund selected or a time‐out occurs during the Cash Accept state. Note counts will be in the escrow notes field (refundable deposit) or the vaulted notes field (direct deposit). ");

                    case "2":
                        System.out.println("Not used");

                    case "3":
                        System.out.println("Error ‐ if counts are included, they are as accurate as the available information allows, except for notes left in escrow in the Close state when the note counts are accurate.");

                    case "4":
                        System.out.println(" Device inoperative ‐ notes are left at the exit slot; counts are included. Usually this is returned counts in the w4 message as notes are at the exit slot.");

                    case "5":
                        System.out.println("No notes in escrow when the Transaction Reply function attempts to vault escrowed notes or return cash, indicating an error at the host.");

                    case "6":
                        System.out.println("Notes detected at power-up.");

                    case "7":
                        System.out.println("Notes not taken, but retracted; counts are included in the Vaulted counts field.");

                    case "8":
                        System.out.println("Not supported");

                    case "?":
                        System.out.println("Counterfeit notes have been detected.");
                        break;
                    case "@":
                        System.out.println("Suspect notes have been detected.");

                    default:
                        System.out.println("Unknown Transaction/Device Status code for w: " + e2_transactionDeviceStatusCode);

                }


                if (StatusInformationField.length > fieldIndex) {
                    String e201_escrowCounts = StatusInformationField[fieldIndex++];
                    System.out.println("  e201 - Escrow Counts (raw 50 chars) : " + e201_escrowCounts + " (1 byte for each of 50 NDC note types, up to 90 notes each)");
                }

                if (StatusInformationField.length > fieldIndex) {
                    String e202_vaultedCounts = StatusInformationField[fieldIndex++];
                    System.out.println("  e202 - Vaulted Counts (raw 50 chars) : " + e202_vaultedCounts + " (1 byte for each of 50 NDC note types, up to 90 notes each)");
                }


                if (StatusInformationField.length > fieldIndex) {
                    String e203_returnedCounts = StatusInformationField[fieldIndex++];
                    System.out.println("  e203 - Returned Counts (raw 50 chars) : " + e203_returnedCounts + " (1 byte for each of 50 NDC note types, up to 90 notes each)");
                }


                if (StatusInformationField.length > fieldIndex) {
                    String e204_totalReturnedToExit = StatusInformationField[fieldIndex++];
                    System.out.println("  e204 - Total Notes Returned to Exit Slot (up to 90) : " + e204_totalReturnedToExit);
                }


                if (StatusInformationField.length > fieldIndex) {
                    String e205_totalInEscrow = StatusInformationField[fieldIndex++];
                    System.out.println("  e205 - Total Notes in Escrow (up to 90) : " + e205_totalInEscrow);
                }

                if (StatusInformationField.length > fieldIndex) {
                    String e206_totalJustVaulted = StatusInformationField[fieldIndex++];
                    System.out.println("  e206 - Total Notes Just Vaulted (up to 90) : " + e206_totalJustVaulted);
                }

                System.out.println("  (Fields e207-e212 for counts >90 are conditional and not parsed in this basic example)");

                if (StatusInformationField.length > fieldIndex) {
                    String e3_errorSeverityCode = StatusInformationField[fieldIndex++];
                    System.out.println("Error Severity (e3) : " + e3_errorSeverityCode + " (Var chars, as described in 'Cash Acceptor Fitness')");
                    // Anda mungkin perlu switch atau logika lain di sini jika ada kode standar untuk e3
                }

                if (StatusInformationField.length > fieldIndex) {
                    String e4_diagnosticStatus = StatusInformationField[fieldIndex++];
                    System.out.println("Diagnostic Status (e4) : " + e4_diagnosticStatus + " (Var chars, M-status plus M-data)");
                }

                if (StatusInformationField.length > fieldIndex) {
                    String e5_suppliesStatusFull = StatusInformationField[fieldIndex++];
                    System.out.println("Supplies Status (e5 - raw) : " + e5_suppliesStatusFull);
                    System.out.print("  Interpreted Supplies Status (e5) codes: ");
                    // Contoh parsing sederhana jika e5_suppliesStatusFull adalah rangkaian kode tunggal karakter
                    for (char suppliesCode : e5_suppliesStatusFull.toCharArray()) {
                        switch (suppliesCode) {
                            case '0':
                                System.out.print("[No change] ");

                            case '1':
                                System.out.print("[Good state] ");

                            case '2':
                                System.out.print("[Bin out (missing or removed)] ");

                            case '3':
                                System.out.print("[Media high (nearly full)] ");

                            case '4':
                                System.out.print("[Media full (overfull)] ");

                            default:
                                System.out.print("[Unknown supplies code: " + suppliesCode + "] ");

                        }
                    }
                    System.out.println();
                }
            case "\\" :
                System.out.println("Envelope Identifier Graphic");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (StatusInformationField[0].substring(1,2)){
                    case "0" :
                        System.out.println("Device Status (" + StatusInformationField[0].substring(0,1) + ") : Envelope presented satisfactorily");
                    case "1" :
                        System.out.println("Device Status (" + StatusInformationField[0].substring(0,1) + ") : Failure - envelope not presented or retracted");
                }
                switch (StatusInformationField[1]){
                    case "0" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : No error/not supported");
                    case "1" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : Routine error");
                    case "2" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : Warning");
                    case "3" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : Suspend");
                    case "4" :
                        System.out.println("Error Severity (" + StatusInformationField[1] + ") : Fatal");
                }
                System.out.println("Diagnostic Status : " + StatusInformationField[2]);
                switch (suppliesStatus){
                    case "1" :
                        System.out.println("Supplies Status (" + suppliesStatus + ") : Sufficient envelopes");
                    case "2" :
                        System.out.println("Supplies Status (" + suppliesStatus + ") : Envelopes low");
                    case "3" :
                        System.out.println("Supplies Status (" + suppliesStatus + ") : Envelopes exhausted");
                }
            case "Y" :
                System.out.println("Coin Dispenser Status");
                System.out.println("Device Identifier Graphic : " + deviceIdentifierGraphic);
                switch (deviceStatus){
                    case "0" :
                        System.out.println(deviceStatus + " : Successful operation, but an exception has occurred, described in the Diagnostic Status field");
                    case "1" :
                        System.out.println(deviceStatus + " : The coin dispenser low threshold for each coin hopper were not set during the configuration of the SST. No coins have been dispensed");
                    case "3" :
                        System.out.println(deviceStatus + " : The coin dispense has not started as the requested hopper is");
                    case "4" :
                        System.out.println(deviceStatus + " : Coins dispensed unknown. The cardholder may have had access to any presented coins, so it should be assumed some may have been dispensed. Intervention may be required to reconcile the cash amount totals. The following counts contain requested dispense values.");
                    case "9" :
                        System.out.println(deviceStatus + " : No coins dispensed or card not ejected. This status is returned on a card before cash transaction if the stack operation fails and the coins are purged prior to card eject.");
                    case ":" :
                        System.out.println(deviceStatus + " : Some coins have been retracted when the coins were not taken following a Present time‐out. The number of coins retracted is unknown");
                }
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