#!/bin/bash
if ! kubectl get namespace devsecops > /dev/null 2>&1; then
    kubectl create namespace devsecops
else
    kubectl delete namespace devsecops
fi

#add execute permissions by running sudo chmod +x 'thisfilename'