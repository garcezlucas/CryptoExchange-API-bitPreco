import axios from "axios";

const API_URL = "http://localhost:8080/api/auth";

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const currentUser = getCurrentUser();

const exchangeInsert = ( market, exchange, value, amount, date) => {
    return axios.post(API_URL + "/exchanges/insert" + currentUser.id, {
        market,
        exchange,
        value,
        amount,
        date
    });
};


const getExchange = () => {
    return axios.get(API_URL + "/exchanges/users/" + currentUser.id);
};

// const getExchangeByExchange = (exchange) => {
//     return axios.get(API_URL + "/exchanges/" + {exchange});
// };

// const putExchangeById = (id, market, exchange, value, amount, date) => {
//     return axios.put(API_URL + "/exchanges/update/" + {id}, {
//         market,
//         exchange,
//         value,
//         amount,
//         date
//     });
// };

// const deleteExchangeById = async (id) => {
//     const response = await axios.delete(API_URL + "/exchanges/delete/" + { id });
//     return response.data;
// };

// const removeAll = () => {
//     return axios.delete(API_URL + "/exchanges");
// };


// const findByExchange = (exchange) => {
//     return axios.get(API_URL + "/exchanges/" + {exchange});
// };



const ExchangeService = {
    exchangeInsert,
    getExchange,
    // getExchangeByExchange,
    // putExchangeById,
    // deleteExchangeById,
    // findByExchange,
    // removeAll
    getCurrentUser
}

export default ExchangeService;