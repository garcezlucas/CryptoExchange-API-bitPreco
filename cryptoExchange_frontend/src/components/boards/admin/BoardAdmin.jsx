import Nav from 'react-bootstrap/Nav';

function BoardAdmin() {
    return (
        <div>
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
    );
}

// const [showElement, setShowElement] = useState(false)
// const showOrHide = () => setShowElement(true)

// return (
//   <div>
//     <button onClick={showOrHide}>Clique em mim</button>
//     { showElement ? <p>Tô aqui</p> : null }
//   </div>
// )

export default BoardAdmin;