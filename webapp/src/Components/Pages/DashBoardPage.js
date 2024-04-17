import {clearPage} from "../../utils/render";
import {getToken} from "../../utils/user";

const viewDashBoard = async () => {
  clearPage();
  const companies = await fetchCompanies();
  await allCompanies(companies);
}

async function fetchCompanies() {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = "http://localhost:3000/companies/getEnterprises";
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}


export default viewDashBoard;