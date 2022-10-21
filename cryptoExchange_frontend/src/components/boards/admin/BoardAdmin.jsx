import Nav from 'react-bootstrap/Nav';

function BoardAdmin() {
    return (
        <Nav variant="pills" defaultActiveKey="/admin">
            <Nav.Item>
        <Nav.Link href="/adminusers">Usuários</Nav.Link>
        </Nav.Item>
        <Nav.Item>
            <Nav.Link href="/admincoins">Criptomoedas</Nav.Link>
        </Nav.Item>
        <Nav.Item>
            <Nav.Link href="/adminexchanges">Transações</Nav.Link>
        </Nav.Item>
        </Nav>
    );
}

export default BoardAdmin;