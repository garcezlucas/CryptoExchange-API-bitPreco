import React from "react";

// import UserService from "../services/User.service";
import AlienCoin from "../images/AlienCoin.png"

const Home = () => {
    // const [content, setContent] = useState("");

    // useEffect(() => {
    //     UserService.getPublicContent().then(
    //         (response) => {
    //             setContent(response.data);
    //         },
    //         (error) => {
    //             const _content =
    //                 (error.response && error.response.data) ||
    //                 error.message ||
    //                 error.toString();

    //             setContent(_content);
    //         }
    //     );
    // }, []);

    

    return (
        <><div>
            <img src={AlienCoin} alt="icon" className='coin-icon' />
        </div>
        
        <div className="container">
                <header className="jumbotron">
                    {/* <h3>{content}</h3> */}
                </header>
        </div></>
    );
};

export default Home;