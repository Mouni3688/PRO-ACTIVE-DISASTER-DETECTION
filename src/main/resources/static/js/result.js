const button = document.getElementById('submit');
const textDisplay = document.getElementById('text-display');
// Add an event listener to the button to listen for a click event
button.addEventListener('click', function() {
    // Show the text by changing the display property
    textDisplay.style.display = 'block';
});











document.querySelector('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const field = document.getElementById('fieldSelect').value;
    const value = document.querySelector('input[name="value"]').value;

    if (!field || !value) {
        alert("Please select a field and enter a value to search.");
        return;
    }

    const response = await fetch('http://localhost:8081/api/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ field, value }),
    });

    if (response.ok) {
        const data = await response.json();
        displayResults(data);
    } else {
        alert("An error occurred while fetching the results.");
    }
});

function displayResults(data) {
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = data.map(user => `
    <div class="result-item">

<br>
<div class="relative overflow-x-auto">
<table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
      <tr>
      <th scope="col" class="px-6 py-3">
             Photo
          </th>
          <th scope="col" class="px-6 py-3">
             Name
          </th>
          <th scope="col" class="px-6 py-3">
             Email
          </th>
          <th scope="col" class="px-6 py-3">
             Phone
          </th>
          <th scope="col" class="px-6 py-3">
              Adress
          </th>
          <th scope="col" class="px-6 py-3">
              Description
          </th>
      </tr>
  </thead>
  <tbody>
      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
          <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
              <img src="${user.picture}" alt="Placeholder Image" width="100" height="100">
          </th>
          <td class="px-6 py-4">
             ${user.name}
          </td>
          <td class="px-6 py-4">
             ${user.email}
          </td>
          <td class="px-6 py-4">
             ${user.phoneNumber}
          </td>
          <td class="px-6 py-4">
             ${user.address}
          </td>
           <td class="px-6 py-4">
            ${user.description}
          </td>
      </tr>
     
  </tbody>
</table>


</div>

      </div>

              <br>
              

    `).join('');
}