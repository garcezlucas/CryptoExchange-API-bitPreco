import Nav from 'react-bootstrap/Nav';

import imageAdmin from "../../../images/admin.jpg"

function BoardAdmin() {
    return (
        <div>
            
            <div className="container-home">
                <div className="container-text">
                    <div class="jumbotron">
                        <h1 class="display-4">Painel de Administrador Admin</h1>
                        <p class="lead"></p>
                        <hr class="my-4"/>
                        <em>Aqui você consegue editar as informações dos usuários, das criptomoedas e das transações.</em>
                    </div>
                    <hr class="my-3"/>
                    <Nav variant="pills" defaultActiveKey="/admin">
                        <Nav.Item>
                        <Nav.Link  href="/adminusers">Usuários</Nav.Link>
                        </Nav.Item>

                        <Nav.Item>
                            <Nav.Link  href="/admincoins">Criptomoedas</Nav.Link>
                        </Nav.Item>

                        <Nav.Item>
                            <Nav.Link  href="/adminexchanges">Transações</Nav.Link>
                        </Nav.Item>
                    </Nav>
                </div>

                <div>
                    <img src={imageAdmin} alt="icon" className='coin-admin' />
                    
                </div>
            </div>
            
        </div>
    );
}

export default BoardAdmin;