package mezzari.torres.lucas.dietbox_challenge.model;

import mezzari.torres.lucas.dietbox_challenge.network.model.NetworkPromise;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    public Flow<Resource<ResultType>> execute() {
        return new Flow<>(new Resource.Loading<>(), (flow) -> {
            ResultType result = loadData();
            if (shouldFetch(result)) {
                fetchData().onSuccess((promise, data) -> {
                    saveData(data);
                    flow.emit(new Resource.Success<>(transform(data)));
                }).onFailure((promise, error) -> {
                    if (result != null) {
                        flow.emit(new Resource.Error<>(error, result));
                        return;
                    }
                    flow.emit(new Resource.Error<>(error));
                });
            }
            flow.emit(new Resource.Success<>(result));
        });
    }

    protected abstract ResultType transform(RequestType data);

    protected abstract void saveData(RequestType data);

    protected abstract boolean shouldFetch(ResultType data);

    protected abstract ResultType loadData();

    protected abstract NetworkPromise<RequestType> fetchData();
}
