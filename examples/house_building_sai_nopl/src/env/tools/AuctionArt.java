package tools;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;

// import needed libraries for java and web3j
// import java.io.File;
// import java.io.IOException;
// import java.math.BigInteger;
// import java.math.BigDecimal;
// import java.net.URISyntaxException;
// import java.net.URL;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.Optional;
// //
// import org.web3j.crypto.Credentials;
// import org.web3j.crypto.WalletUtils;
// import org.web3j.protocol.Web3j;
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
public class AuctionArt extends Artifact {


    @OPERATION public void init(String taskDs, int maxValue)  {
        // observable properties
        defineObsProperty("task",          taskDs);
        defineObsProperty("maxValue",      maxValue);
        defineObsProperty("currentBid",    maxValue);
        defineObsProperty("currentWinner", "no_winner");
    }

    @OPERATION public void bid(double bidValue) {
        ObsProperty opCurrentValue  = getObsProperty("currentBid");
        ObsProperty opCurrentWinner = getObsProperty("currentWinner");
	//log("received bid " + bidValue + " from " + getOpUserName() + "  current Value " + getObsProperty("currentBid") + " - " + getObsProperty("currentWinner"));
        if (bidValue < opCurrentValue.doubleValue()) {  // the bid is better than the previous
            opCurrentValue.updateValue(bidValue);
            opCurrentWinner.updateValue(getOpUserName());
        }
    }

}
