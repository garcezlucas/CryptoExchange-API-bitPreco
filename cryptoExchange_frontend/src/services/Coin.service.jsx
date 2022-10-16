import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const getBitcoin = () => {
    return axios.get(API_URL + "btc-brl/ticker");
};

const getEthereum = () => {
    return axios.get(API_URL + "eth-brl/ticker");
};

const getUSDCoin = () => {
    return axios.get(API_URL + "usdc-brl/ticker");
};

const CoinService = {
    getBitcoin,
    getEthereum,
    getUSDCoin
}

export default CoinService;

