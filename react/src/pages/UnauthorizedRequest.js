import React from 'react'

function UnauthorizedRequest() {
    return (
        <div style={{
            "margin": "2% 2% 0 2%",
            "fontWeight": "bold"
        }} class="alert alert-danger" role="alert">
            Unauthorized Request...
        </div>
    )
}

export default UnauthorizedRequest
