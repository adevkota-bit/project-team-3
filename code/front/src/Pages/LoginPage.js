import axios from 'axios';
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';


export default function LoginPage() {

    let navigate = useNavigate();

    const [user, setUsers] = useState({
        username:"",
        password:""
    });

    const onInputChange = (e) =>{
        setUsers({...user, [e.target.name]:e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8082/api/v1/auth/signin", user);
            const  token  = response.data;

            localStorage.setItem('access_token', token.access_token);
            navigate('/home');

        } catch (error) {
            console.log("error from loginPage")
        }
    };

    return (

        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4'> HRMasery Login</h2>


                    <form onSubmit={handleSubmit}>
                        <div className='mb-3'>
                            <label htmlFor="Name" className='form-label'>
                                UserName
                            </label>

                            <input
                                type={"text"}
                                name="username"
                                className='form-control'
                                value={user.username}
                                onChange={onInputChange}
                                placeholder='Enter Username'
                            >
                            </input>
                        </div>

                        <div className='mb-3'>
                            <label htmlFor="jobTitle" className='form-label'>
                                Password
                            </label>
                            <input
                                type={"text"}
                                name="password"
                                className='form-control'
                                value={user.password}
                                onChange={onInputChange}
                                placeholder='Password'
                            >
                            </input>
                        </div>

                        <button type='submit' className='bnt btn-outline-primary'>
                            Login
                        </button>

                        <Link className='bnt btn-outline-primary' to={'/registration'}>
                            New user
                        </Link>

                    </form>

                </div>
            </div>
        </div>

    )
}
