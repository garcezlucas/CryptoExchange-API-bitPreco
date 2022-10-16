import React, {useState} from 'react'
import BuyForm from './BuyForm'
// import btc from '../assets/btc.png'
// import eth from '../assets/eth.png'
// import ltc from '../assets/ltc.png'

const Home = () => {

    // const tiles = [
    //     {id: 1,  name: 'BTC', rate: 135000},
    //     {id: 2,  name: 'ETH', rate: 7500},
    //     {id: 3,  name: 'LTC', rate: 250},
    // ]

    const [selectedTile, setSelectedTile] = useState("")

    const handleSelect = (data) => {
        setSelectedTile(data)
    }

    

    return (
        <div >
            
            <BuyForm data={selectedTile}/>
        </div>
    )
}

export default Home


