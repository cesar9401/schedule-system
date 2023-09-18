<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import router from '@/router';
import { onMounted, ref } from 'vue';

const academicCycleId = ref(1);

onMounted(() => {
  const acCyId = router.currentRoute.value.params['academicCycleId'];
  academicCycleId.value = Number(acCyId);
});

</script>

<template>
  <div class="container">
    <Header :type-header="HeaderEnum.HEADER_CRUD" :to="'/academic-cycle/' + academicCycleId + '/model'" title="Regresar a simulaciones" />
    <div class="my-5">
      <h1 class="text-success text-center">Agregar simulaci&oacute;n</h1>
    </div>
    <form>
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Usuario responsable</label>
          <input id="name" class="form-control" placeholder="Eje: Jose Perez Leon">
        </div>
        <div class="mb-3 col-lg-6 col-12">
          <label for="description">Descripci&oacute;n</label>
          <input id="description" class="form-control" placeholder="Eje: Esta es la simulacion del modelo...">
        </div>
      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-2">Guardar</button>
      </div>
    </form>
  </div>
</template>
