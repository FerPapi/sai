/* auxiliary rules for agents */

event_goal(E[source(self)],E).

i_am_winning(Art)   // check if I placed the current best bid on auction artifact Art
   :- currentWinner(W)[artifact_id(Art)] &
      .my_name(Me) & .term2string(Me,MeS) & W == MeS.


/* Organisational Plans Required by all agents */

// plans to handle obligations
// obligation to commit to a mission
+obligation(Ag,Norm,committed(Ag,Mission,Scheme),Deadline)
    : .my_name(Ag)
   <- println("I am obliged to commit to ",Mission," on ",Scheme,"... Waiting for SAI to set the commitment...").


// obligation to achieve a goal
// ============================
//+obligation(Ag,Norm,achieved(Scheme,Goal,Ag),Deadline) //the agent perceives the obligation following the NPL notation
+obligation(Ag,R,done(Scheme,Goal,Ag),Deadline) //the agent perceives the obligation following the NPL notation
   : .my_name(Ag) &
    constitutive_rule(sai__freestandingY, done(Scheme,Goal,Ag),ToDo[sai__agent(Ag)],M)  //The agent looks for a constitutive rule defining how the goal is achieved
   <-
   println("I am obliged to ",Goal,". I found a constitutive rule that shows me. I have to produce the event ", ToDo);
   if (ToDo=payment(Price, AgentReceiver)) {
       println("---xx---");
       println("Price: ", Price)
       println("Receiver: ", AgentReceiver)
       println("ToDo: ", ToDo);
    //    println("Recipient Agent: ", sai__agent(RecipientAgent));
       println("sai__agent(Ag): ", sai__agent(Ag));
    //    println("constitutive_rule(X,done(Scheme,Goal,Ag),ToDo[sai__agent(Ag)],M)", constitutive_rule(X,done(Scheme,Goal,Ag),ToDo[sai__agent(Ag)],M));
       joinWorkspace("wsp_payment_infra",I);
       lookupArtifact("bank",Bank);
       focus(Bank);
       .wait(100); //to avoid some problems: ORA4MAS seems being processing previous goal states when SAI constitutes some new achievements. To check.
       ToDo[artifact_id(Bank)];
       println("---xx---");
   }
   else {
       println("-x-");
       println(ToDo[artifact_id(House)]);
       println(M);
       println("-x-");
       joinWorkspace("wsp_house",I);
       lookupArtifact("housegui",House);
       focus(House);
       .wait(100); //to avoid some problems: ORA4MAS seems being processing previous goal states when SAI constitutes some new achievements. To check.
       ToDo[artifact_id(House)];
   }
   .

// +obligation(Ag,R,done(Scheme,Goal,Ag),Deadline) //the agent perceives the obligation following the NPL notation
//    : .my_name(Ag) &
//    constitutive_rule(X,paid(Scheme,Goal,Ag),ToDo[sai__agent(Ag)], M) & //The agent looks for a constitutive rule defining how the goal is achieved
//    X==sai__freestandingY & T\==true
//    <-
//    println("I am HERE");
//    .

// obligation to achieve a goal
+obligation(Ag,achieved(Scheme,Goal,Ag),Deadline)
    : .my_name(Ag)
   <-       println(" ---> working to achieve ",Goal);
      //?jcm__ws("wsp_house",WspHouse); //look to the house workspace
   	//  cartago.set_current_wsp(WspHouse);
      !Goal;
      println(" <--- done").

// an unknown type of obligation was received
+obligation(Ag,R,What,DeadLine)
   : .my_name(Ag)
   <-
   println("I ", Ag, " am obliged to ", What, ", but I don't know what to do!").
