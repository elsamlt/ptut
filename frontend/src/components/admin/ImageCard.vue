<template>
  <div class="image-container">
    <v-img
      :src="`${image.img}`"
      aspect-ratio="1"
      class="bg-grey-lighten-2"
      cover
    >
      <template v-slot:placeholder>
        <v-row
          align="center"
          class="fill-height ma-0"
          justify="center"
        >
          <v-progress-circular
            color="grey-lighten-5"
            indeterminate
          ></v-progress-circular>
        </v-row>
      </template>

      <!-- Overlay avec le bouton de suppression -->
      <div class="overlay-buttons">
        <v-btn class="btn" size="40px" @click="deleteImage">
          <v-icon class="icon">mdi-delete</v-icon>
        </v-btn>
      </div>
    </v-img>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  image: Object,
  index: Number
});

const emit = defineEmits(["delete", "edit"]);

const deleteImage = () => {
  emit("delete", props.image.id);
};

const editImage = () => {
  emit("edit", props.image);
};
</script>

<style scoped>
.image-container {
  position: relative;
  width: 100%;
  height: 100%;
}

.overlay-buttons {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  background-color: rgba(0, 0, 0, 0.5);
  transition: opacity 0.3s ease;
}

.image-container:hover .overlay-buttons {
  opacity: 1;
}

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  z-index: 2;
}

.btn:hover .icon {
  transform: scale(0.8);
}
</style>
