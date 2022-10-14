import React, {useState, useEffect} from 'react'
import CryptoTile from './CriptoTile'
import BuyForm from './BuyForm'
import Transactions from './Transactions'

const Shopping = () => {


    const tiles = [
        {id: 1, name: 'BTC', rate: 135000},
        {id: 2, name: 'ETH', rate: 7500},
        {id: 3, name: 'LTC', rate: 250},
    ]

    const [selectedTile, setSelectedTile] = useState(tiles[0])
    const [list, setList] = useState([])

    const handleSelect = (data) => {
        setSelectedTile(data)
    }

    const buildList = (list) => {
        setList(list)
    }

    return (
        <div >
            <div>
                <div>
                    <div >
                        {
                            tiles.map(
                                (coin) =>(
                                    <CryptoTile 
                                        key={coin.id}
                                        data={coin}
                                        onClick={handleSelect}
                                        selectedTile={coin.id === selectedTile.id}
                                    />
                                )
                            )
                        }
                    </div>
                </div>
                <BuyForm data={selectedTile} onPurchase={buildList}/>
                
            </div>
        </div>
    )
}

export default Shopping
