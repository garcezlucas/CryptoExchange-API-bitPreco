import Nav from 'react-bootstrap/Nav';

function BoardPremium() {
    return (
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
    );
}

export default BoardPremium;