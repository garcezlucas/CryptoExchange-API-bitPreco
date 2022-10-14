import React from 'react'


const InputBase = ({label, ...props}) => (
    <>
        <input type="number" {...props} placeholder={label}/>
    </>
)

export default InputBase
