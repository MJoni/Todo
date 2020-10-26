
document.querySelector('button[id="delConfirmButton"]').addEventListener('click', function(stop){
    stop.preventDefault();
    $('#deleteModal').modal('hide');
    let userId = document.cookie;
    //let taskId = 7; need to selected item from the table/display
    delTask(9, userId); 
    $('#messageModal').modal('show');
    console.log("End of query")
  });

function delTask(taskId, userId) {
    fetch("http://localhost:3000/api/tasks/" + taskId, {
        method: 'delete',
        headers: {
            "Content-type": "application/json"
        },
    })

        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
           //Authenticate before delete it and send appropiate message

           document.getElementById("show-msg").innerHTML="A task has been deleted";
            //   let mydiv = document.getElementById("create");
            //   mydiv.className ="alert alert-danger"
            //   mydiv.textContent ="Student Deleted";
            //   removeEl(mydiv)


        })
        .catch(function (error) {
            console.log('Request failed', error);
            document.getElementById("show-msg").innerHTML="No Task found!";
            //   let mydiv = document.getElementById("create");
            //   mydiv.className ="alert alert-success"
            //   mydiv.textContent ="Error deleting student";
            //   removeEl(mydiv)
        });
}