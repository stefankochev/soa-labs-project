import { useKeycloak } from "@react-keycloak/web";
import { Link } from "react-router-dom";

const Nav = () => {
    const { keycloak } = useKeycloak();

    return (
        <div>
            <div className="top-0 w-full flex flex-wrap">
                <section className="x-auto">
                    <nav className="flex justify-between bg-gray-200 text-blue-800 w-screen">
                        <div className="px-5 xl:px-12 py-6 flex w-full items-center">
                            <h1 className="text-3xl font-bold font-heading">
                                Keycloak React AUTH.
                            </h1>
                            <ul className="hidden md:flex px-4 mx-auto font-semibold font-heading space-x-12">
                                <li>
                                    <Link to="/" className="hover:text-blue-800">Home</Link>
                                </li>
                                <li>
                                    <Link to="/profile" className="hover:text-blue-800">Profile</Link>
                                </li>
                            </ul>
                            <div className="hidden xl:flex items-center space-x-5">
                                <div className="hover:text-gray-200">
                                    {!keycloak.authenticated && (
                                        <button
                                            type="button"
                                            className="text-blue-800"
                                            onClick={() => keycloak.login()}
                                        >
                                            Login
                                        </button>
                                    )}

                                    {!!keycloak.authenticated && (
                                        <button
                                            type="button"
                                            className="text-blue-800"
                                            onClick={() => keycloak.logout({ redirectUri: 'http://localhost:3000/' })}
                                        >
                                            Logout ({keycloak.tokenParsed?.preferred_username})
                                        </button>
                                    )}
                                </div>
                            </div>
                        </div>
                    </nav>
                </section>
            </div>
        </div>
    );
};

export default Nav;