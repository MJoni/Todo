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
      // let errorCreate = document.getElementsById("calenderModal");
      // errorCreate.className = "alert alert-danger";
      // errorCreate.textContent ="Cannot create task!";
      // removeEl (errorCreate)
    });
}
function readIntoString(data,userId){
  for (let index = 0; index < data.length; index++) {
    if(userId== data[index].users_user_id){
      let title = data[index].title;
      let body =data[index].body;
      let start_date = data[index].start_date;
      let due_date = data[index].due_date; 
      displayResult(title,body,start_date, due_date);

    }   
  }

}
function displayResult(title,body,start_date, due_date){
  var contain = document.getElementById("display-result");
  var tr = document.createElement('tr');
  var td = document.createElement('td');
  
  let innerDiv = document.createElement('div');
  innerDiv.className ="card";
  let page_title = document.createElement('h4');
  page_title.className = "card-header";
  page_title.innerHTML =title;
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
  // bodyDiv.appendChild(par_body,s_date,d_date);
  // innerDiv.appendChild(bodyDiv,page_title)
  td.appendChild(innerDiv);
  tr.appendChild(td);
  contain.appendChild(tr);



}