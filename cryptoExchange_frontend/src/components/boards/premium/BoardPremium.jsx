import Nav from 'react-bootstrap/Nav';

import imagePremium from "../../../images/premium.jpg"

function BoardPremium() {
    return (
        <div>
            <div className="container-home">
                <div className="container-text">
                    <div class="jumbotron">
                        <h1 class="display-4">Painel de Administrador Premium</h1>
                        <p class="lead"></p>
                        <hr class="my-4"/>
                        <em>Aqui você consegue visualizar toodas as informações dos usuários, das criptomoedas e das transações.</em>
                    </div>
                    <hr class="my-3"/>
                    <Nav variant="pills" defaultActiveKey="/premium">
                        <Nav.Item>
                            <Nav.Link href="/premiumusers">Usuários</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="/premiumcoins">Criptomoedas</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="/premiumexchanges">Transações</Nav.Link>
                        </Nav.Item>
                    </Nav>
                </div>
                <div>
                    <img src={imagePremium} alt="icon" className='coin-premium' />
                    
                </div>
            </div>
        </div>
    );
}

export default BoardPremium;