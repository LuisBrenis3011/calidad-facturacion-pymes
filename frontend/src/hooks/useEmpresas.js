import { useContext, useReducer, useState } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import { empresasReducer } from "../reducers/empresasReducer";
import { findAll, save, update, remove } from "../services/empresaService";
import { AuthContext } from "../auth/context/AuthContext";

const initialEmpresas = [];

const initialEmpresaForm = {
    id: 0,
    razonSocial: '',
    ruc: '',
    email: '',
    direccion: '',
};

const initialErrors = {
    razonSocial: '',
    ruc: '',
    email: '',
};

export const useEmpresas = () => {
    const [empresas, dispatch] = useReducer(empresasReducer, initialEmpresas);
    const [empresaSelected, setEmpresaSelected] = useState(initialEmpresaForm);
    const [visibleForm, setVisibleForm] = useState(false);
    const [errors, setErrors] = useState(initialErrors);
    const navigate = useNavigate();
    const { handlerLogout } = useContext(AuthContext);

    const getEmpresas = async () => {
        try {
            const result = await findAll();
            dispatch({
                type: 'loadingEmpresas',
                payload: result.data
            });
        } catch (error) {
            if (error.response?.status === 401) handlerLogout();
        }
    };

    const handlerAddEmpresa = async (empresa) => {
        let response;
        try {
            if (empresa.id === 0) {
                response = await save(empresa);
            } else {
                response = await update(empresa);
            }

            dispatch({
                type: empresa.id === 0 ? 'addEmpresa' : 'updateEmpresa',
                payload: response.data
            });

            Swal.fire(
                empresa.id === 0 ? 'Empresa Creada' : 'Empresa Actualizada',
                empresa.id === 0
                    ? 'La empresa ha sido registrada con éxito!'
                    : 'La empresa ha sido actualizada correctamente!',
                'success'
            );

            handlerCloseForm();
            navigate('/empresas');

        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrors(error.response.data);
            } else if (error.response?.status === 401) {
                handlerLogout();
            } else {
                throw error;
            }
        }
    };

    const handlerRemoveEmpresa = (id) => {
        Swal.fire({
            title: '¿Seguro que deseas eliminar la empresa?',
            text: "Esta acción no se puede deshacer!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar!',
            cancelButtonText: 'Cancelar'
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await remove(id);
                    dispatch({
                        type: 'removeEmpresa',
                        payload: id
                    });
                    Swal.fire('Eliminada!', 'La empresa fue eliminada con éxito.', 'success');
                } catch (error) {
                    if (error.response?.status === 401) handlerLogout();
                }
            }
        });
    };

    const handlerEmpresaSelectedForm = (empresa) => {
        setVisibleForm(true);
        setEmpresaSelected({ ...empresa });
    };

    const handlerOpenForm = () => {
        setVisibleForm(true);
    };

    const handlerCloseForm = () => {
        setVisibleForm(false);
        setEmpresaSelected(initialEmpresaForm);
        setErrors({});
    };

    return {
        empresas,
        empresaSelected,
        visibleForm,
        initialEmpresaForm,
        errors,
        handlerAddEmpresa,
        handlerRemoveEmpresa,
        handlerEmpresaSelectedForm,
        handlerOpenForm,
        handlerCloseForm,
        getEmpresas
    };
};
