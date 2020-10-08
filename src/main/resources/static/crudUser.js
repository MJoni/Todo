document.querySelector('button[id="log-out"]').addEventListener("click",function(stop){
    stop.preventDefault();
    deleteCookie();
    console.log("log out successfully")

  })

  //get user data in the form
  document.querySelector('button[id="my-profile"]').addEventListener("click",function(stop){
    stop.preventDefault();
    // GET data from database;
    //loop the jason format into separate varibles
    //add and populate them accordingly

    //console.log("log out successfully")

  })

  //patch user new data in the data
  document.querySelector('button[id="update-user"]').addEventListener("click",function(stop){
    stop.preventDefault();
    //get all the field values 
    //PUT into data base 
    //close modal with correct message
    console.log("log out successfully")

  })

  //delete a user account
  document.querySelector('button[id="delete-user"]').addEventListener("click",function(stop){
    stop.preventDefault();
    let userId = document.cookie;
    console.log(userId);
    deleteUser(userId);

  })

function deleteUser(userId){
    console.log(userId);
    fetch("http://localhost:3000/api/users/" + userId, {
        method: 'delete',
        headers: {
            "Content-type": "application/json"
        },
    })

        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
           //Authenticate before delete it and send appropiate message
           deleteCookie();


        })
        .catch(function (error) {
            console.log('Request failed', error);
            document.getElementById("show-msg").innerHTML="No User found!";
        });

}

function deleteCookie(){
    document.cookie = "; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.href= "index.html";
}

