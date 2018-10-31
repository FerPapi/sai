pragma solidity ^0.4.25;

contract BankArtifactMAS {

/*    struct Auction {
        string task;
        uint256 maxValue;
        uint256 currentBid;
        string currentWinner; //use addresses for each agent in future
    }
*/
    struct Account {
        bool accountActive;
        string client;
        uint256 accountValue;
    }


//    mapping (string => Auction) auctionList; // Works like a non iterable dictionary in python
//    uint public auctionCount = 0;

    mapping (string => Account) bank;

    constructor() public {
    }
/*    function getAuctionCount() view public returns (uint) {
        return auctionCount;
    }
*/

/*    function CreateAuction( string _task,
                            uint256 _maxValue,
                            uint256 _currentBid,
                            string _currentWinner) public returns (uint auctionId) {

        auctionCount++;
        auctionList[_task].task = _task;
        auctionList[_task].maxValue = _maxValue;
        auctionList[_task].currentBid = _currentBid;
        auctionList[_task].currentWinner = _currentWinner;

        return auctionId;
    }
*/
    function CreateAccount( string _clientName,
                            uint256 _initialValue) public {
        Account storage newAccount = bank[_clientName];
        if (!newAccount.accountActive) {
            newAccount.client = _clientName;
            newAccount.accountValue = _initialValue;
            newAccount.accountActive = true;
        }

    }
/*
    function placeBid(string task, uint bidValue, string bidder) public {
        Auction storage a = auctionList[task];
        if (a.currentBid > bidValue){
            a.currentBid = bidValue;
            a.currentWinner = bidder;
        }

    }
*/
/*
    function getCurrentWinnerbyAuctionID(string task) public view returns (string winner) {
        Auction storage a = auctionList[task];
        return a.currentWinner;
    }

    function getCurrentBidbyAuctionID(string task) public view returns (uint curretBid) {
        Auction storage a = auctionList[task];
        return a.currentBid;
    }
*/
    function checkValue(string _clientName) public view returns (uint accountValue) {
        Account storage account = bank[_clientName];
        return account.accountValue;

    }

    function depositValue(string _clientName, uint256 _value) public {
        Account storage account = bank[_clientName];
        uint256 currentValue = account.accountValue;
        bank[_clientName].accountValue = currentValue + _value;
    }

    function transferValue(uint256 _transferValue, string _clientName, string _creditor) public
    returns (string confirmationMessage) {
        Account storage sendingAccount = bank[_clientName];
        Account storage receivingAccount = bank[_creditor];
        uint256 sendingAccountValue = sendingAccount.accountValue;
        if (!receivingAccount.accountActive) {
            confirmationMessage = "Error: No account for this client";
            return confirmationMessage;
        }
        if (sendingAccountValue < _transferValue) {
            confirmationMessage = "Error: No funds available";
            return confirmationMessage;
        }
        sendingAccount.accountValue = sendingAccountValue - _transferValue;
        receivingAccount.accountValue = receivingAccount.accountValue + _transferValue;
        confirmationMessage = "Transfer Successful!";
        return confirmationMessage;
    }

}
