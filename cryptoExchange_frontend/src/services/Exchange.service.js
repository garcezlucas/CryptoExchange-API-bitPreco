import api from '../api/api'

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const currentUser = getCurrentUser();

class ExchangeDataService {

    exchangeInsert (market, exchange, value, amount, price, date) {
        return api.post("/auth/exchanges/" + currentUser.id, {
            market,
            exchange,
            value,
            amount,
            price,
            date
        });
    };

    getAllExchange () {
        return api.get("/auth/exchanges/all");
    };

    getExchangeById (id) {
        return api.get(`/auth/exchanges/list/${id}`);
    };

    getExchangeByUserId () {
        return api.get("/auth/exchanges/users/" + currentUser.id);
    };

    getExchangeByExchange (exchange) {
    return api.get(`/auth/exchanges/${exchange}`);
    };

    updateExchangeById (id, exchange) {
        return api.put(`/auth/exchanges/update/${id}`, exchange);
    };

    
    deleteExchangeById (id) {
        return api.delete(`/auth/exchanges/delete/${id}`);
    };

    deleteAll () {
        return api.delete("/auth/exchanges/delete/all");
    }

}

export default new ExchangeDataService;
