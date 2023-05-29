import { useKeycloak } from "@react-keycloak/web";

type Props = {
    children: JSX.Element
}

const ProtectedRoute = ({ children }: Props): JSX.Element | null => {

    const { keycloak, initialized } = useKeycloak();

    const isLoggedIn = keycloak.authenticated;

    if (!initialized) {
        return <div>Loading...</div>
    }

    if (!isLoggedIn) {
        keycloak.login()
        return null;
    }

    return children;
}

export { ProtectedRoute };
export default ProtectedRoute;