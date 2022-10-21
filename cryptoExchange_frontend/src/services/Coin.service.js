import api from '../api/api'

class CoinDataService {

    getCoin (market) {
        return api.get(`/auth/${market}-brl/ticker`);
    };

    getAllCoins () {
        return api.get("/auth/coins/list/all");
    };

    async getInfosCoin (market) {
        const response = await api.get(`/auth/coins/${market}-brl`);
        if (response.data) {
            localStorage.setItem("Coin", JSON.stringify(response.data));
        }
        ;
    };

    findCoinByMarket (market){
        return api.get(`/auth/coins/find/${market}-brl`)
    }

    getCoinById(id) {
        return api.get(`/auth/coins/list/${id}`);
    };

    updateCoinById (id, coin) {
        return api.put(`/auth/coins/update/${id}`, coin);
    };

    deleteCoinById(id){
        return api.delete(`/auth/coins/delete/${id}`);
    }

    deleteAll () {
        return api.delete("/auth/coins/delete/all");
    }
}

export default new CoinDataService;