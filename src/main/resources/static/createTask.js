//create new task
document.querySelector("form.create-task").addEventListener("submit", function(stop){
    stop.preventDefault();
    let formElement  = document.querySelector("form.create-task").elements;
    
    // let userId = formElement["userId"].value;
    let userId = document.cookie;
    let title = formElement["title"].value;
    let startDate = formElement["dueDate"].value;
    let dueDate = formElement["dueDate"].value
    let body = formElement["body"].value;
    console.log(userId, title, startDate, dueDate, body);
    newTask(title, startDate,dueDate,body,userId);
    $('#createNewModal').modal('hide');
    $('#messageModal').modal('show');
  


  })
 
function newTask(title,start_date,due_date,body,user_id){

    let update_user_id = parseInt(user_id);

    let dataToPost ={
        
            "title": title,
            "start_date": start_date,
            "due_date": due_date,
            "body": body,
            "user_id": update_user_id
    }
    fetch( "http://localhost:3000/api/tasks", 
    {
    method: 'post',
    headers: {
      "Content-type": "application/json"
    },
    body: JSON.stringify(dataToPost)
    })
    .then(function (data) {

        console.log('Request succeeded with JSON response', data);
        document.getElementById("show-msg").innerHTML="New Task Created";
                
    })
    .catch(function (error) {
        console.log('Request failed', error);
        document.getElementById("show-msg").innerHTML="Failed to create new Task";
    });
}

