import React from 'react';

const HistoryTransactions = ({list}) => {

    return (
        <div >

            <h4>Lista de transações</h4>
            {

                list.length ? list.map(

                    (item) => (
                        <div>
                            <div key={item.id} >
                                <div className='transaction-name'>{item.id}</div>
                                <div className='transaction-name'><strong>{item.name}</strong></div>
                                <div className='transaction-value'>{item.converted}</div>
                            </div>
                        </div>
                        
                    )

                ):(
                    <div>
                        <h5>
                            Lista de transações vazia
                        </h5>
                    </div>
                )

            }
        </div>
    )
}

export default HistoryTransactions