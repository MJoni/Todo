//it works
// const userId = document.cookie
// fetch('http://localhost:3000/api/tasks/')
// .then(
//   function (response) {
//     if (response.status !== 200) {
//       console.log('Looks like there was a problem. Status Code: ' +
//         response.status);
//       return;
//     }
//     // Examine the text in the response
//     response.json().
//       then(function (data) {
//         if(data.length==0){
//           return;
//         }
//         // let table = document.querySelector("table");
//         // let data = Object.keys(data1[0]);
//         readIntoString(data,userId)

//         // generateTableHead(table, data);
//         // generateTable(table, data1);
//       })
// });

//task id search bar listener
document.querySelector('form.form-inline').addEventListener('submit', function (stop) {
  stop.preventDefault();
  let formElement = document.querySelector("form.form-inline").elements;
  let token = formElement[0].value;
  const userId = document.cookie
  searchForTask(token, userId);  
});


function searchForTask(token, userId) {
  fetch('http://localhost:3000/api/tasks/' + token)
    .then(
      function (response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
        // Examine the text in the response
        response.json().
          then(function (data) {
            if(data.length==0){
              return;
            }
            // let table = document.querySelector("table");
            // let data = Object.keys(data1[0]);
            readIntoString(data,userId)

            // generateTableHead(table, data);
            // generateTable(table, data1);
          });
      }
    )
    .catch(function (err) {
      console.log('Fetch Error :-S', err);
    });
}
function readIntoString(data,userId){
  for (let index = 0; index < data.length; index++) {
    if(userId== data[index].user_id){
      let task_id = data[index].task_id;
      let title = data[index].title;
      let body =data[index].body;
      let start_date = data[index].start_date;
      let due_date = data[index].due_date; 
      displayResult(task_id,title,body,start_date, due_date);

    }   
  }

}
function displayResult(task_id,title,body,start_date, due_date){
  var contain = document.getElementById("display-result");
  var tr = document.createElement('tr');
  var td = document.createElement('td');
  td.addEventListener('click',function(){
    document.getElementById("deleteAtask").disabled= false;
    document.getElementById("updateAtask").disabled= false;

    update_taskId=task_id;
    update_title= title;
    update_start_date =start_date;
    update_due_date =due_date;
    update_body =body;
    console.log(update_taskId, update_title,body, start_date,due_date);
  });
  
  let innerDiv = document.createElement('div');
  innerDiv.className ="card bg-dark";
  let page_title = document.createElement('h4');
  page_title.className = "card-header";
  page_title.innerHTML = task_id +". "+title;
  let bodyDiv = document.createElement('div');
  bodyDiv.className = "card-body";
  let par_body = document.createElement('p');
  par_body.className = "card-text";
  par_body.innerHTML = body;
  let s_date = document.createElement('h6');
  s_date.innerHTML = "Start Date: "+start_date;
  let d_date = document.createElement('h6');
  d_date.innerHTML = "Due Date: "+due_date;
  bodyDiv.innerHTML += par_body.outerHTML + s_date.outerHTML + d_date.outerHTML;
  innerDiv.innerHTML += page_title.outerHTML + bodyDiv.outerHTML;
  td.appendChild(innerDiv);
  tr.appendChild(td);
  contain.appendChild(tr);
}

var update_taskId; //collect the recent seleted item
var update_title, update_start_date, update_due_date, update_body;

//has the same function in deletetask.js file
document.querySelector('button[id="delConfirmButton"]').addEventListener('click', function(stop){
  stop.preventDefault();
  $('#deleteModal').modal('hide');
  let userId = document.cookie;
  delTask(update_taskId, userId); 
  $('#messageModal').modal('show');
  console.log("End of query")
});
function delTask(updatedTaskId) {
  fetch("http://localhost:3000/api/tasks/" + updatedTaskId, {
      method: 'delete',
      headers: {
          "Content-type": "application/json"
      },
  })

      .then(function (data) {
          console.log('Request succeeded with JSON response', data);
         document.getElementById("show-msg").innerHTML="A task has been deleted";
         document.getElementById("deleteAtask").disabled= true;


      })
      .catch(function (error) {
          console.log('Request failed', error);
          document.getElementById("show-msg").innerHTML="No Task found!";
          document.getElementById("deleteAtask").disabled= true;
          //   let mydiv = document.getElementById("create");
          //   mydiv.className ="alert alert-success"
          //   mydiv.textContent ="Error deleting student";
          //   removeEl(mydiv)
      });
}

//   update tasks
document.querySelector('button[id="#updateTask"]').addEventListener("click",function(stop){
  stop.preventDefault();
  //get all the value from selected row
  let formElement  = document.querySelector("form.update-task").elements;
  let id = update_taskId;  
  let title = formElement["title"].value;
  let start_date = formElement["start_date"].value;
  let due_date = formElement["due_date"].value;
  let body = formElement["body"].value
  let user_id =document.cookie;

  //extract and populate inside the update modal accordingly
  //either click update to 'patch' method to server or close to remove    
  // upon update show appropiate message.


  updateTask(id,title,start_date,due_date,body,user_id);
  $('#updateModal').modal('hide');

})

function updateTask(id,title,start_date,due_date,body,user_id){
  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  
    let dataToUpdate ={
          "title": title,
          "start_date": start_date,
          "due_date": due_date,
          "body": body,
          "user_id": user_id
  }
  var requestOptions = {
    method: 'PATCH',
    headers: myHeaders,
    body: JSON.stringify(dataToUpdate),
    redirect: 'follow'
  };
  
  fetch("http://localhost:3000/api/tasks/"+id, requestOptions)
    // .then(response => response.text())
    // .then(result => console.log(result))
    // .catch(error => console.log('error', error));
  .then(function (data) {

      console.log('Request succeeded with JSON response', data);
      document.getElementById("show-msg").innerHTML="Task Updated";
      $('#messageModal').modal('show');
              
  })
  .catch(function (error) {
      console.log('Request failed', error);
      document.getElementById("show-msg").innerHTML="Failed to create new Task";
  });
}
//auto populate update table
document.getElementById("updateAtask").addEventListener('click',function(stop)
{
  stop.preventDefault();
  document.getElementById("title").value =update_title;
  document.getElementById("start_date").value =update_start_date;
  document.getElementById("due_date").value =update_due_date;
  document.getElementById("body").value =update_body;
})

if(window.location.reload){
  document.getElementById("deleteAtask").disabled= true;
  document.getElementById("updateAtask").disabled= true;
}