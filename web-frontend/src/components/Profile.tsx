import { useKeycloak } from "@react-keycloak/web";

const Profile = () => {

    const { keycloak } = useKeycloak();

    return (
        <>
            <h2 className="font-bold text-2xl ml-4">
                Welcome, &nbsp;
                <span className="font-semibold text-blue-800">
                    {keycloak.tokenParsed?.preferred_username}
                </span>
            </h2>
            {
                keycloak.token &&
                <button
                    className="border border-black px-4 py-2 rounded-sm ml-4 mt-2 uppercase text-white bg-blue-800"
                    onClick={() => keycloak.token && navigator.clipboard.writeText(keycloak.token)}
                >
                    Copy token to clipboard !
                </button>
            }
        </>
    )
}

export { Profile };
export default Profile;