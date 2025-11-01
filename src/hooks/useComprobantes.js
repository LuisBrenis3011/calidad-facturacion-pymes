import { useReducer, useState, useEffect } from "react";
import { comprobanteReducer } from "../reducers/comprobanteReducer.js";
import Swal from "sweetalert2";

const initialComprobanteForm = {
    id: 0,
    tipo: "BOLETA",
    numeroComprobante: "",
    fecha: new Date().toISOString().split('T')[0],
    clienteNombre: "",
    clienteDocumento: "",
    items: [],
    total: 0
};

export const useComprobantes = (empresaId) => {
    const [comprobantes, dispatch] = useReducer(comprobanteReducer, []);
    const [comprobanteSelected, setComprobanteSelected] = useState(initialComprobanteForm);
    const [visibleForm, setVisibleForm] = useState(false);
    const [errors, setErrors] = useState({});

    // Clave única por empresa en localStorage
    const STORAGE_KEY = `comprobantes_empresa_${empresaId}`;

    // Cargar comprobantes del localStorage cuando cambia empresaId
    useEffect(() => {
        if (empresaId) {
            getComprobantes();
        }
    }, [empresaId]);

    // Obtener comprobantes del localStorage
    const getComprobantes = () => {
        try {
            const stored = localStorage.getItem(STORAGE_KEY);
            const comprobantesData = stored ? JSON.parse(stored) : [];
            dispatch({
                type: "loadingComprobantes",
                payload: comprobantesData
            });
        } catch (error) {
            console.error("Error al cargar comprobantes:", error);
        }
    };

    // Guardar en localStorage
    const saveToLocalStorage = (comprobantesData) => {
        try {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(comprobantesData));
        } catch (error) {
            console.error("Error al guardar en localStorage:", error);
        }
    };

    // Generar número de comprobante automático
    const generateNumeroComprobante = (tipo) => {
        const tipoPrefix = tipo === "BOLETA" ? "B001" : "F001";
        const existentes = comprobantes.filter(c => c.tipo === tipo);
        const numero = (existentes.length + 1).toString().padStart(5, '0');
        return `${tipoPrefix}-${numero}`;
    };

    // Agregar o actualizar comprobante
    const handlerAddComprobante = (comprobante) => {
        // Validaciones
        if (!comprobante.clienteNombre || !comprobante.clienteDocumento || comprobante.items.length === 0) {
            setErrors({
                clienteNombre: !comprobante.clienteNombre ? "El nombre del cliente es obligatorio" : "",
                clienteDocumento: !comprobante.clienteDocumento ? "El documento del cliente es obligatorio" : "",
                items: comprobante.items.length === 0 ? "Debe agregar al menos un producto" : ""
            });
            return;
        }

        try {
            let nuevoComprobante;
            let nuevosComprobantes;

            if (comprobante.id === 0) {
                // Crear nuevo
                nuevoComprobante = {
                    ...comprobante,
                    id: Date.now(), // ID único basado en timestamp
                    numeroComprobante: generateNumeroComprobante(comprobante.tipo),
                    empresaId: empresaId,
                    fecha: comprobante.fecha || new Date().toISOString().split('T')[0]
                };

                dispatch({ type: "addComprobante", payload: nuevoComprobante });
                nuevosComprobantes = [...comprobantes, nuevoComprobante];

                Swal.fire("Comprobante creado", `${comprobante.tipo} ${nuevoComprobante.numeroComprobante} creada con éxito`, "success");
            } else {
                // Actualizar existente
                nuevoComprobante = {
                    ...comprobante,
                    empresaId: empresaId
                };

                dispatch({ type: "updateComprobante", payload: nuevoComprobante });
                nuevosComprobantes = comprobantes.map(c =>
                    c.id === nuevoComprobante.id ? nuevoComprobante : c
                );

                Swal.fire("Comprobante actualizado", "El comprobante fue actualizado con éxito", "info");
            }

            // Guardar en localStorage
            saveToLocalStorage(nuevosComprobantes);

            handlerCloseForm();
            setErrors({});
        } catch (error) {
            console.error(error);
            Swal.fire("Error", "No se pudo guardar el comprobante", "error");
        }
    };

    // Eliminar comprobante
    const handlerRemoveComprobante = (id) => {
        Swal.fire({
            title: "¿Estás seguro?",
            text: "No podrás revertir esto",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar"
        }).then((result) => {
            if (result.isConfirmed) {
                try {
                    dispatch({ type: "removeComprobante", payload: id });
                    const nuevosComprobantes = comprobantes.filter(c => c.id !== id);
                    saveToLocalStorage(nuevosComprobantes);
                    Swal.fire("Eliminado", "El comprobante fue eliminado", "success");
                } catch (error) {
                    console.error(error);
                    Swal.fire("Error", "No se pudo eliminar el comprobante", "error");
                }
            }
        });
    };

    const handlerOpenForm = () => {
        setComprobanteSelected({
            ...initialComprobanteForm,
            fecha: new Date().toISOString().split('T')[0]
        });
        setVisibleForm(true);
    };

    const handlerCloseForm = () => {
        setVisibleForm(false);
        setComprobanteSelected(initialComprobanteForm);
        setErrors({});
    };

    const handlerComprobanteSelectedForm = (comprobante) => {
        setComprobanteSelected(comprobante);
        setVisibleForm(true);
    };

    return {
        comprobantes,
        comprobanteSelected,
        visibleForm,
        errors,
        initialComprobanteForm,

        getComprobantes,
        handlerAddComprobante,
        handlerRemoveComprobante,
        handlerOpenForm,
        handlerCloseForm,
        handlerComprobanteSelectedForm
    };
};