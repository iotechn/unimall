// translate router.meta.title, be used in breadcrumb sidebar tagsview
import i18n from "@/lang/index.js";
export function generateTitle(title) {
  const hasKey = i18n.te('route.' + title)

  if (hasKey) {
    // $t :this method from vue-i18n, inject in @/lang/index.js
    const translatedTitle = i18n.t('route.' + title)

    return translatedTitle
  }
  return title
}
