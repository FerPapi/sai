package tools;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;
import java.util.HashMap;

// import needed libraries for java and web3j
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
// //
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
// import org.web3j.protocol.core.DefaultBlockParameterName;
// import org.web3j.protocol.core.methods.request.Transaction;
// import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
// import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
// import org.web3j.protocol.core.methods.response.EthSendTransaction;
// import org.web3j.protocol.core.methods.response.TransactionReceipt;
// import org.web3j.protocol.core.methods.response.Web3ClientVersion;
// import org.web3j.protocol.http.HttpService;
// import org.web3j.protocol.parity.Parity;
// import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;
// import org.web3j.tx.ClientTransactionManager;
// import org.web3j.tx.TransactionManager;
// import org.web3j.utils.Convert;
// import org.web3j.abi.datatypes.generated.Uint256;
// import org.web3j.abi.datatypes.Utf8String;
//
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.concurrent.Future;
// import org.web3j.abi.TypeReference;
// import org.web3j.abi.datatypes.Function;
// import org.web3j.abi.datatypes.Type;
// import org.web3j.tx.Contract;



/**
 *      Artifact that implements the auction.
 */
public class BankArt extends Artifact {

    private HashMap<String, Integer> accountValue = new HashMap<String, Integer>();

    @OPERATION public void init(){
        // observable properties
        // defineObsProperty("currentState", accountValue);
        System.out.println("Initializing Banking Artifact...");
    }

    @OPERATION public void makeAccount(int initialValue) {
        // ObsProperty opCurrentBalance = getObsProperty("currentBalance");
        System.out.println("Making account for " + getOpUserName() + " with " + initialValue);
        accountValue.put(getOpUserName(), initialValue);
    }

    @OPERATION public void depositValue(int value) {
        // ObsProperty opCurrentBalance = getObsProperty("currentBalance");
        String client = getOpUserName();
        System.out.println("Depositing " + value + " in the account of " + client);
        accountValue.put(client, accountValue.get(client)+value);
    }

    @OPERATION public void checkValue(OpFeedbackParam<Integer> value) {
        // ObsProperty opCurrentBalance = getObsProperty("currentBalance");
        String client = getOpUserName();
        System.out.println("Checking account value for " + client + " : " +  accountValue.get(client));
        value.set(accountValue.get(client));
    }

    @OPERATION public void payment(int value) {
        // ObsProperty opCurrentBalance = getObsProperty("currentBalance");
        System.out.println("==XXX PAYMENT OF" + value + " XXX===");
    }

    @OPERATION public void transferValue(int value, String recipientAgent) {
        // ObsProperty opCurrentBalance = getObsProperty("currentBalance");
        String client = getOpUserName();
        System.out.println("Transfering " + value + " from " + client + " to " + recipientAgent);
        if (accountValue.containsKey(recipientAgent)) {
            if (accountValue.get(client) >= value) {
                accountValue.put(recipientAgent, accountValue.get(recipientAgent)+value);
                accountValue.put(client, accountValue.get(client)-value);
                System.out.println("Done!");
                Integer accValue = accountValue.get(recipientAgent);
                System.out.println("Value in "+ recipientAgent+ " account: "+ Integer.toString(accValue));
            }
            else {
                System.out.println("Insufficient Funds");
            }
        }
        else {
            System.out.println("Client not found: " + recipientAgent);
        }

    }



}
