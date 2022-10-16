import React, { useState, useEffect } from "react";

import CoinService from "../../services/Coin.service";
import EventBus from "../../common/EventBus";

const Coin = () => {
    const [content, setContent] = useState("");
    const [name, setName] = useState("content.market");

    useEffect(() => {
        CoinService.getEthereum().then(
            (response) => {
                setContent(response.data);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setContent(_content);

                if (error.response && error.response.status === 401) {
                    EventBus.dispatch("logout");
                }
            }
        );
    }, []);

    



    return (
        <div>
            {content.market}
            {content.buy}
        </div>

    );
};



export default Coin;